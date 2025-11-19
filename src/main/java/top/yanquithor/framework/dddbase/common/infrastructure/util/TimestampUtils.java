package top.yanquithor.framework.dddbase.common.infrastructure.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimestampUtils {
    
    private TimestampUtils(){}
    
    public static LocalDateTime fromTimestamp(long timestamp) {
        Instant instant = Instant.ofEpochSecond(timestamp);
        ZoneId zoneId = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zoneId);
    }
    
    public static long toTimestamp(LocalDateTime time) {
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = time.atZone(zoneId).toInstant();
        return instant.toEpochMilli();
    }
}
