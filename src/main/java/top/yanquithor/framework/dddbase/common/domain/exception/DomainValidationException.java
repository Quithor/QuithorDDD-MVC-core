package top.yanquithor.framework.dddbase.common.domain.exception;

/**
 * 领域验证异常 - 当业务规则验证失败时抛出
 *
 * @author YanQuithor
 * @version 1.1.1.11
 * @since 2025-12-13
 */
public class DomainValidationException extends DomainException {

    public DomainValidationException(String message) {
        super("VALIDATION_ERROR", message);
    }

    public DomainValidationException(String message, Throwable cause) {
        super("VALIDATION_ERROR", message, cause);
    }
}