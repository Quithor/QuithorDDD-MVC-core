package top.yanquithor.framework.dddbase.common.domain.exception;

/**
 * 领域不变量异常 - 当领域不变量被破坏时抛出
 *
 * @author YanQuithor
 * @version 1.1.1.11
 * @since 2025-12-13
 */
public class InvariantViolationException extends DomainException {

    public InvariantViolationException(String message) {
        super("INVARIANT_VIOLATION", message);
    }

    public InvariantViolationException(String message, Throwable cause) {
        super("INVARIANT_VIOLATION", message, cause);
    }
}