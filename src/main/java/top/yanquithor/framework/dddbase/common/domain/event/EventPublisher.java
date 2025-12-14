package top.yanquithor.framework.dddbase.common.domain.event;

/**
 * Event Publisher Interface
 *
 * @author YanQuithor
 * @version 1.1.1.10
 * @since 2025-12-13
 */
public interface EventPublisher {
    // 发布领域事件
    void publish(DomainEvent event);
    // 订阅特定类型的领域事件
    <T extends DomainEvent> void subscribe(Class<T> eventType, EventHandler<T> handler);
}