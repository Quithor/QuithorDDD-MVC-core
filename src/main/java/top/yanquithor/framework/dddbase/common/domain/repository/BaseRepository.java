package top.yanquithor.framework.dddbase.common.domain.repository;

import top.yanquithor.framework.dddbase.common.domain.model.Aggregate;

public interface BaseRepository <DOMAIN extends Aggregate> {
    DOMAIN save(DOMAIN domain);
    
    Long count(DOMAIN domain);
    
    DOMAIN update(DOMAIN domain);
    
    DOMAIN delete(DOMAIN domain);
    
    DOMAIN getById(long id);
}
