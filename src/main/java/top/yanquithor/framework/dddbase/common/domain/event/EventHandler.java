package top.yanquithor.framework.dddbase.common.domain.event;

/**
 * Event Handler Interface
 *
 * @author YanQuithor
 * @version 1.1.1.10
 * @since 2025-12-13
 */
@FunctionalInterface
public interface EventHandler<T extends DomainEvent> {
    // 处理领域事件
    void handle(T event);
}