package top.yanquithor.framework.dddbase.common.domain.service.impl;

import top.yanquithor.framework.dddbase.common.domain.model.Aggregate;
import top.yanquithor.framework.dddbase.common.domain.event.EventPublisher;
import top.yanquithor.framework.dddbase.common.domain.repository.BaseRepository;
import top.yanquithor.framework.dddbase.common.domain.exception.BusinessRuleViolationException;

import java.util.HashMap;
import java.util.Map;

/**
 * 领域服务协调器 - 用于协调跨多个聚合的复杂业务规则
 *
 * @author YanQuithor
 * @version 1.1.1.14
 * @since 2025-12-13
 */
public class DomainServiceCoordinator {

    private final EventPublisher eventPublisher;
    private final Map<Class<? extends Aggregate>, BaseRepository<? extends Aggregate>> repositories;

    public DomainServiceCoordinator(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
        this.repositories = new HashMap<>();
    }

    /**
     * 注册聚合对应的仓储
     */
    @SuppressWarnings("unchecked")
    public <T extends Aggregate> void registerRepository(
            Class<T> aggregateClass, 
            BaseRepository<T> repository) {
        this.repositories.put(aggregateClass, repository);
    }

    /**
     * 执行跨聚合的业务操作
     */
    public <T extends Aggregate> T executeCrossAggregateOperation(
            Class<T> resultType, 
            CrossAggregateOperation operation) {
        
        try {
            // 执行跨聚合操作
            T result = operation.execute();
            
            // 发布结果聚合的事件
            if (result != null) {
                result.getDomainEvents().forEach(eventPublisher::publish);
                result.clearDomainEvents();
            }
            
            return result;
        } catch (Exception e) {
            throw new BusinessRuleViolationException("Cross-aggregate operation failed: " + e.getMessage(), e);
        }
    }

    /**
     * 跨聚合操作的函数式接口
     */
    @FunctionalInterface
    public interface CrossAggregateOperation<T extends Aggregate> {
        T execute();
    }
}