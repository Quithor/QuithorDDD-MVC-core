package top.yanquithor.framework.dddbase.common.domain.exception;

/**
 * 领域权限异常 - 当领域操作权限不足时抛出
 *
 * @author YanQuithor
 * @version 1.1.1.11
 * @since 2025-12-13
 */
public class DomainPermissionException extends DomainException {

    public DomainPermissionException(String message) {
        super("PERMISSION_DENIED", message);
    }

    public DomainPermissionException(String message, Throwable cause) {
        super("PERMISSION_DENIED", message, cause);
    }
}