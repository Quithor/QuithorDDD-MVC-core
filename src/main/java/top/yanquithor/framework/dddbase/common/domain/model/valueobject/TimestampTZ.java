package top.yanquithor.framework.dddbase.common.domain.model.valueobject;

import java.time.Instant;
import java.time.ZoneId;

/**
 * Timestamp with Timezone Value Object
 *
 * @author YanQuithor
 * @version 1.1.1
 * @since 2025-12-13
 */
public record TimestampTZ(Instant instant, ZoneId zoneId) {
}
