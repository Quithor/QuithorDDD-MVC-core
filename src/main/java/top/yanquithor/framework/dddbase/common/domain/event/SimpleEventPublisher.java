package top.yanquithor.framework.dddbase.common.domain.event;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * Simple Event Publisher Implementation
 *
 * @author YanQuithor
 * @version 1.1.1.10
 * @since 2025-12-13
 */
public class SimpleEventPublisher implements EventPublisher {
    // 存储事件处理器的映射表
    private final Map<Class<?>, List<EventHandler<?>>> handlers = new ConcurrentHashMap<>();

    @Override
    public void publish(DomainEvent event) {
        // 获取指定事件类型的处理器列表
        List<EventHandler<?>> eventHandlers = handlers.get(event.getClass());
        if (eventHandlers != null) {
            for (EventHandler<?> handler : eventHandlers) {
                // 使用原始类型是因为泛型擦除，这里需要做类型转换
                @SuppressWarnings("unchecked")
                EventHandler<DomainEvent> typedHandler = (EventHandler<DomainEvent>) handler;
                typedHandler.handle(event);
            }
        }
    }

    @Override
    public <T extends DomainEvent> void subscribe(Class<T> eventType, EventHandler<T> handler) {
        // 为指定事件类型注册处理器
        handlers.computeIfAbsent(eventType, k -> new ArrayList<>()).add(handler);
    }
}