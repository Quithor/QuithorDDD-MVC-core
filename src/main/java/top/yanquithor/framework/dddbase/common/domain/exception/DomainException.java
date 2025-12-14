package top.yanquithor.framework.dddbase.common.domain.exception;

/**
 * 领域异常基类
 *
 * @author YanQuithor
 * @version 1.1.1.11
 * @since 2025-12-13
 */
public class DomainException extends RuntimeException {

    private final String errorCode;

    public DomainException(String message) {
        super(message);
        this.errorCode = "DOMAIN_ERROR";
    }

    public DomainException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public DomainException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "DOMAIN_ERROR";
    }

    public DomainException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}