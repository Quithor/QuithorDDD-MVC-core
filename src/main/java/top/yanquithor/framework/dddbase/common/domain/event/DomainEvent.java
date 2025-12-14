package top.yanquithor.framework.dddbase.common.domain.event;

import java.time.LocalDateTime;

/**
 * Domain Event Interface
 *
 * @author YanQuithor
 * @version 1.1.1.10
 * @since 2025-12-13
 */
public interface DomainEvent {
    // 获取事件ID
    String getEventId();
    // 获取发生时间
    LocalDateTime getOccurredOn();
}