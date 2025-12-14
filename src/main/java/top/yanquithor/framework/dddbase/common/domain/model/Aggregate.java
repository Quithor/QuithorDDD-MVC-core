package top.yanquithor.framework.dddbase.common.domain.model;

import top.yanquithor.framework.dddbase.common.domain.event.DomainEvent;
import top.yanquithor.framework.dddbase.common.domain.validation.ValidationSupport;

import java.util.List;

/**
 * Aggregate Root Interface
 *
 * @author YanQuithor
 * @version 1.1.1.15
 * @since 2025-12-13
 */
public interface Aggregate extends ValidationSupport {
    List<DomainEvent> getDomainEvents();
    void clearDomainEvents();
}
