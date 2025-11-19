package top.yanquithor.framework.dddbase.common.infrastructure.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component
public class SnowFlake {
    
    // Define constants
    private static final long EPOCH = LocalDateTime.of(2025, 1, 1, 0, 0, 0).toInstant(ZoneOffset.UTC).toEpochMilli();
    private static final int WORKER_ID_BITS = 5; // Worker ID bits
    private static final int SEQUENCE_BITS = 12; // Sequence number bits
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS); // Maximum worker ID
    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BITS); // Maximum sequence number
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS; // Worker ID left shift bits
    private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS; // TimestampTZ left shift bits
    
    private final long workerId; // Current worker ID
    private final AtomicLong sequence = new AtomicLong(0L); // Sequence number
    private long lastTimestamp = -1L; // Last timestamp when ID was generated
    
    @Autowired
    public SnowFlake(Environment environment,
                     @Value("${snowflake.worker-id:0}") int defaultWorkerId) {
        // Prioritize reading worker ID from environment variables
        String workerIdFromEnv = environment.getProperty("SNOWFLAKE_WORKER_ID");
        this.workerId = (workerIdFromEnv != null) ? Long.parseLong(workerIdFromEnv) : defaultWorkerId;
        if (this.workerId < 0 || this.workerId > MAX_WORKER_ID) {
            throw new IllegalArgumentException(String.format(
                    "Worker Id can't be greater than %d or less than 0", MAX_WORKER_ID));
        }
        log.info("Snowflake Worker Id: {}", this.workerId);
    }
    
    public SnowFlake() {this.workerId = 1;}
    
    public synchronized long nextId() {
        long timestamp = timeGen();
        
        // If current time is less than the last timestamp when ID was generated,
        // it means the system clock has been set back
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format(
                    "Clock moved backwards. Refusing to generate id for %d milliseconds",
                    lastTimestamp - timestamp));
        }
        
        // If multiple IDs are generated within the same millisecond,
        // resolve conflicts through sequence numbers
        if (lastTimestamp == timestamp) {
            sequence.getAndIncrement();
            if (sequence.get() > MAX_SEQUENCE) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence.set(0L); // Reset sequence number in new millisecond
        }
        
        lastTimestamp = timestamp;
        
        return ((timestamp - EPOCH) << TIMESTAMP_LEFT_SHIFT) |
                (workerId << WORKER_ID_SHIFT) |
                (sequence.get() & MAX_SEQUENCE);
    }
    
    private long timeGen() {
        return System.currentTimeMillis();
    }
    
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }
}
