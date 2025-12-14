package top.yanquithor.framework.dddbase.common.domain.exception;

/**
 * 领域业务规则异常 - 当违反业务规则时抛出
 *
 * @author YanQuithor
 * @version 1.1.1.11
 * @since 2025-12-13
 */
public class BusinessRuleViolationException extends DomainException {

    public BusinessRuleViolationException(String message) {
        super("BUSINESS_RULE_VIOLATION", message);
    }

    public BusinessRuleViolationException(String message, Throwable cause) {
        super("BUSINESS_RULE_VIOLATION", message, cause);
    }
}