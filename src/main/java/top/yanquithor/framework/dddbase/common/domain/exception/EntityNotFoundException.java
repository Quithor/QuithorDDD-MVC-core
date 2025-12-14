package top.yanquithor.framework.dddbase.common.domain.exception;

/**
 * 领域实体未找到异常 - 当找不到指定的领域实体时抛出
 *
 * @author YanQuithor
 * @version 1.1.1.11
 * @since 2025-12-13
 */
public class EntityNotFoundException extends DomainException {

    private final String entityType;
    private final Object entityId;

    public EntityNotFoundException(String entityType, Object entityId) {
        super("ENTITY_NOT_FOUND", String.format("%s with id %s not found", entityType, entityId));
        this.entityType = entityType;
        this.entityId = entityId;
    }

    public EntityNotFoundException(String entityType, Object entityId, String message) {
        super("ENTITY_NOT_FOUND", message);
        this.entityType = entityType;
        this.entityId = entityId;
    }

    public String getEntityType() {
        return entityType;
    }

    public Object getEntityId() {
        return entityId;
    }
}