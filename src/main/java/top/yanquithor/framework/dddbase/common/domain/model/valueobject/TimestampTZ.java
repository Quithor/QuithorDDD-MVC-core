package top.yanquithor.framework.dddbase.common.domain.model.valueobject;

import java.time.Instant;
import java.time.ZoneId;

public record TimestampTZ(Instant instant, ZoneId zoneId) {
}
