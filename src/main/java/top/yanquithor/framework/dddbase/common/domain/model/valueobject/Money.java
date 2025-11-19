package top.yanquithor.framework.dddbase.common.domain.model.valueobject;

import java.math.BigDecimal;

public record Money(BigDecimal amount, String currency) {
}
