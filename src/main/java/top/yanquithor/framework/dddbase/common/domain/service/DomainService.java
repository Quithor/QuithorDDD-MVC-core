package top.yanquithor.framework.dddbase.common.domain.service;

import top.yanquithor.framework.dddbase.common.domain.model.Aggregate;

public interface DomainService<DOMAIN extends Aggregate> {
    
    DOMAIN create(DOMAIN domain);
    
    Long count(DOMAIN domain);
    
    DOMAIN update(DOMAIN domain);
    
    DOMAIN delete(DOMAIN domain);
    
    DOMAIN getById(long id);
}
