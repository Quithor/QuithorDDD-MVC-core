package top.yanquithor.framework.dddbase.common.application.service;

import top.yanquithor.framework.dddbase.common.domain.model.Aggregate;
import top.yanquithor.framework.dddbase.common.domain.service.DomainService;
import top.yanquithor.framework.dddbase.common.infrastructure.domain.service.impl.DomainServiceImpl;

import java.util.List;

/**
 * Application Service Abstract Base Class
 *
 * @author YanQuithor
 * @version 1.1.1.10
 * @since 2025-12-13
 */
public abstract class ApplicationService<DOMAIN extends Aggregate, DS extends DomainServiceImpl<DOMAIN>> {

    private final DomainService<DOMAIN> domainService;

    protected ApplicationService(DS domainService) {
        this.domainService = domainService;
    }

}
