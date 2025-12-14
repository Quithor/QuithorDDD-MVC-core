package top.yanquithor.framework.dddbase.common.application.service;

import top.yanquithor.framework.dddbase.common.domain.model.Aggregate;
import top.yanquithor.framework.dddbase.common.domain.service.DomainService;

import java.util.List;

/**
 * Application Service Abstract Base Class
 *
 * @author YanQuithor
 * @version 1.1.1.11
 * @since 2025-12-13
 */
public abstract class ApplicationService<DOMAIN extends Aggregate> {

    private final DomainService<DOMAIN> domainService;

    protected ApplicationService(DomainService<DOMAIN> domainService) {
        this.domainService = domainService;
    }

}
