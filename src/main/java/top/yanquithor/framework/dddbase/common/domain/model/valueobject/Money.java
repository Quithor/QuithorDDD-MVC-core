package top.yanquithor.framework.dddbase.common.domain.model.valueobject;

import java.math.BigDecimal;

/**
 * Money Value Object
 *
 * @author YanQuithor
 * @version 1.1.1
 * @since 2025-12-13
 */
public record Money(BigDecimal amount, String currency) {
}
