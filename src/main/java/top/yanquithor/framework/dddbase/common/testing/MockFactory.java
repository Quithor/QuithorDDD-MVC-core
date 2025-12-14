package top.yanquithor.framework.dddbase.common.testing;

import top.yanquithor.framework.dddbase.common.domain.model.Aggregate;
import top.yanquithor.framework.dddbase.common.domain.repository.BaseRepository;
import top.yanquithor.framework.dddbase.common.domain.service.DomainService;
import top.yanquithor.framework.dddbase.common.domain.event.EventPublisher;
import top.yanquithor.framework.dddbase.common.domain.event.DomainEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Mock工厂 - 用于创建测试所需的Mock对象
 *
 * @author YanQuithor
 * @version 1.1.1.16
 * @since 2025-12-13
 */
public class MockFactory {

    /**
     * 创建Mock的事件发布者
     */
    public static EventPublisher createMockEventPublisher() {
        return new MockEventPublisherImpl();
    }

    /**
     * 创建Mock的仓储实现
     */
    public static <T extends Aggregate> BaseRepository<T> createMockRepository() {
        return new MockRepositoryImpl<>();
    }

    /**
     * Mock事件发布者实现，提供事件跟踪功能
     */
    public static class MockEventPublisherImpl implements EventPublisher {
        private final List<DomainEvent> publishedEvents = new CopyOnWriteArrayList<>();

        @Override
        public void publish(DomainEvent event) {
            publishedEvents.add(event);
        }

        @Override
        public <T extends DomainEvent> void subscribe(Class<T> eventType, top.yanquithor.framework.dddbase.common.domain.event.EventHandler<T> handler) {
            // Mock实现，仅记录订阅
        }

        public List<DomainEvent> getPublishedEvents() {
            return publishedEvents;
        }

        public void clearEvents() {
            publishedEvents.clear();
        }
    }

    /**
     * Mock仓储实现
     */
    private static class MockRepositoryImpl<T extends Aggregate> implements BaseRepository<T> {
        private final Map<Long, T> storage = new HashMap<>();
        private long nextId = 1;

        @Override
        public T save(T domain) {
            if (domain == null) {
                throw new IllegalArgumentException("Domain cannot be null");
            }
            // 模拟ID分配
            storage.put(nextId++, domain);
            return domain;
        }

        @Override
        public Long count(T domain) {
            return (long) storage.size();
        }

        @Override
        public T update(T domain) {
            // 简单的更新实现，实际应用中可能需要更复杂的逻辑
            return domain;
        }

        @Override
        public T delete(T domain) {
            // 简单的软删除实现
            return domain;
        }

        @Override
        public void hardDelete(T domain) {
            // 模拟硬删除
            storage.values().remove(domain);
        }

        @Override
        public T getById(long id) {
            return storage.get(id);
        }
    }
}