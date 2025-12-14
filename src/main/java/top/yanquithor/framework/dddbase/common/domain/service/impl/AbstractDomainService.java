package top.yanquithor.framework.dddbase.common.domain.service.impl;

import top.yanquithor.framework.dddbase.common.domain.model.Aggregate;
import top.yanquithor.framework.dddbase.common.domain.event.EventPublisher;

import java.util.List;

/**
 * 抽象领域服务基类 - 提供领域服务的基础功能
 *
 * @author YanQuithor
 * @version 1.1.1.14
 * @since 2025-12-13
 */
public abstract class AbstractDomainService {

    protected final EventPublisher eventPublisher;

    public AbstractDomainService(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * 执行领域操作并发布相关事件
     *
     * @param aggregate 聚合对象
     * @param operation 领域操作
     * @param <T> 聚合类型
     * @return 操作结果
     */
    protected <T extends Aggregate> T executeDomainOperation(
            T aggregate,
            DomainOperation<T> operation) {

        if (aggregate == null) {
            throw new IllegalArgumentException("Aggregate cannot be null");
        }

        // 执行业务操作
        T result = operation.execute(aggregate);

        // 发布聚合中的事件
        if (result != null) {
            result.getDomainEvents().forEach(eventPublisher::publish);
            result.clearDomainEvents();
        }

        return result;
    }

    /**
     * 执行跨多个聚合的领域操作
     *
     * @param aggregates 聚合列表
     * @param operation 领域操作
     * @param <T> 聚合类型
     * @return 操作结果
     */
    protected <T extends Aggregate> T executeCrossAggregateOperation(
            List<T> aggregates,
            CrossAggregateOperation<T> operation) {

        if (aggregates == null || aggregates.isEmpty()) {
            throw new IllegalArgumentException("Aggregates list cannot be null or empty");
        }

        // 执行跨聚合业务操作
        T result = operation.execute(aggregates);

        // 发布所有聚合中的事件
        for (T aggregate : aggregates) {
            if (aggregate != null) {
                aggregate.getDomainEvents().forEach(eventPublisher::publish);
                aggregate.clearDomainEvents();
            }
        }

        return result;
    }

    /**
     * 领域操作函数式接口
     */
    @FunctionalInterface
    protected interface DomainOperation<T extends Aggregate> {
        T execute(T aggregate);
    }

    /**
     * 跨聚合操作函数式接口
     */
    @FunctionalInterface
    protected interface CrossAggregateOperation<T extends Aggregate> {
        T execute(List<T> aggregates);
    }
}