package top.yanquithor.framework.dddbase.common.domain.model;

import top.yanquithor.framework.dddbase.common.domain.event.DomainEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract Aggregate Base Class
 *
 * @author YanQuithor
 * @version 1.1.1
 * @since 2025-12-13
 */
public abstract class AbstractAggregate implements Aggregate {
    // 聚合的领域事件列表
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    @Override
    public List<DomainEvent> getDomainEvents() {
        // 返回当前聚合的所有未处理领域事件
        return new ArrayList<>(domainEvents);
    }

    @Override
    public void clearDomainEvents() {
        // 清空领域事件列表
        domainEvents.clear();
    }

    protected void addDomainEvent(DomainEvent event) {
        // 添加领域事件
        domainEvents.add(event);
    }

    protected void removeDomainEvent(DomainEvent event) {
        // 移除领域事件
        domainEvents.remove(event);
    }
}