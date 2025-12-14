package top.yanquithor.framework.dddbase.common.domain.model;

import top.yanquithor.framework.dddbase.common.domain.event.DomainEvent;
import top.yanquithor.framework.dddbase.common.domain.validation.ValidationResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract Aggregate Base Class
 *
 * @author YanQuithor
 * @version 1.1.1.17
 * @since 2025-12-13
 */
public abstract class AbstractAggregate implements Aggregate {
    // 聚合的领域事件列表
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    // 聚合的唯一标识
    protected Object id;

    // 聚合的版本号
    protected Long version;

    @Override
    public Object getId() {
        return id;
    }

    @Override
    public Long getVersion() {
        return version;
    }

    @Override
    public void setVersion(Long version) {
        this.version = version;
    }

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

    @Override
    public ValidationResult validate() {
        // 默认实现：返回成功验证结果
        // 子类可根据需要重写此方法实现具体业务验证逻辑
        return ValidationResult.success();
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