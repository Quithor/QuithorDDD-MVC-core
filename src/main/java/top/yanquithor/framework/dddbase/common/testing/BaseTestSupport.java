package top.yanquithor.framework.dddbase.common.testing;

import top.yanquithor.framework.dddbase.common.domain.event.EventPublisher;
import top.yanquithor.framework.dddbase.common.domain.repository.BaseRepository;
import top.yanquithor.framework.dddbase.common.domain.model.Aggregate;
import top.yanquithor.framework.dddbase.common.domain.event.DomainEvent;

import java.util.List;

/**
 * 测试基类 - 提供通用的测试功能和Mock对象
 *
 * @author YanQuithor
 * @version 1.1.1.16
 * @since 2025-12-13
 */
public abstract class BaseTestSupport {

    /**
     * 创建Mock的事件发布者
     */
    protected EventPublisher createMockEventPublisher() {
        return MockFactory.createMockEventPublisher();
    }

    /**
     * 创建Mock的仓储
     */
    protected <T extends Aggregate> BaseRepository<T> createMockRepository() {
        return MockFactory.createMockRepository();
    }

    /**
     * 验证事件是否已发布
     */
    @SuppressWarnings("unchecked")
    protected boolean verifyEventPublished(EventPublisher publisher, Class<? extends DomainEvent> eventType) {
        if (publisher instanceof MockFactory.MockEventPublisherImpl) {
            MockFactory.MockEventPublisherImpl mockPublisher = (MockFactory.MockEventPublisherImpl) publisher;
            List<DomainEvent> publishedEvents = mockPublisher.getPublishedEvents();
            return publishedEvents.stream()
                    .anyMatch(event -> eventType.isAssignableFrom(event.getClass()));
        }
        return false;
    }

    /**
     * 重置Mock事件发布者
     */
    protected void resetEventPublisher(EventPublisher publisher) {
        if (publisher instanceof MockFactory.MockEventPublisherImpl) {
            ((MockFactory.MockEventPublisherImpl) publisher).clearEvents();
        }
    }

    /**
     * 获取已发布的事件列表
     */
    protected List<DomainEvent> getPublishedEvents(EventPublisher publisher) {
        if (publisher instanceof MockFactory.MockEventPublisherImpl) {
            return ((MockFactory.MockEventPublisherImpl) publisher).getPublishedEvents();
        }
        return null;
    }
}