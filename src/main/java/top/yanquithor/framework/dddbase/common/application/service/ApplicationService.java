package top.yanquithor.framework.dddbase.common.application.service;

import top.yanquithor.framework.dddbase.common.domain.model.Aggregate;
import top.yanquithor.framework.dddbase.common.domain.service.DomainService;
import top.yanquithor.framework.dddbase.common.infrastructure.domain.service.impl.DomainServiceImpl;

import java.util.List;

public abstract class ApplicationService<DOMAIN extends Aggregate, DS extends DomainServiceImpl<DOMAIN>> {

    private final DomainService<DOMAIN> domainService;
    
    private ApplicationService(DS domainService) {
        this.domainService = domainService;
    }
    
}
