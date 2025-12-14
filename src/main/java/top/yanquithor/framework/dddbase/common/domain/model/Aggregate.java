package top.yanquithor.framework.dddbase.common.domain.model;

import top.yanquithor.framework.dddbase.common.domain.event.DomainEvent;
import top.yanquithor.framework.dddbase.common.domain.validation.ValidationSupport;

import java.util.List;

/**
 * Aggregate Root Interface
 *
 * @author YanQuithor
 * @version 1.1.1.17
 * @since 2025-12-13
 */
public interface Aggregate extends ValidationSupport {
    // 获取聚合的唯一标识
    Object getId();

    // 获取聚合的版本号
    Long getVersion();

    // 设置聚合的版本号
    void setVersion(Long version);

    // 获取聚合的所有领域事件
    List<DomainEvent> getDomainEvents();

    // 清空聚合的领域事件
    void clearDomainEvents();

    // 检查聚合是否为新创建的
    default boolean isNew() {
        return getId() == null;
    }
}
