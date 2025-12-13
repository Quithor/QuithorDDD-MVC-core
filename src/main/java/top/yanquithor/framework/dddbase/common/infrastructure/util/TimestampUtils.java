package top.yanquithor.framework.dddbase.common.infrastructure.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Timestamp Utility Class - Provides conversion between timestamps and LocalDateTime
 *
 * @author YanQuithor
 * @version 1.1.1
 * @since 2025-12-13
 */
public class TimestampUtils {

    private TimestampUtils(){}

    // 从时间戳转换为LocalDateTime
    public static LocalDateTime fromTimestamp(long timestamp) {
        Instant instant = Instant.ofEpochSecond(timestamp);
        ZoneId zoneId = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zoneId);
    }

    // 从LocalDateTime转换为时间戳
    public static long toTimestamp(LocalDateTime time) {
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = time.atZone(zoneId).toInstant();
        return instant.toEpochMilli();
    }
}
