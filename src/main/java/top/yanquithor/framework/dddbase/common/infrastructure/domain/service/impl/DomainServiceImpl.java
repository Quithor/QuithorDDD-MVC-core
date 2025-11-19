package top.yanquithor.framework.dddbase.common.infrastructure.domain.service.impl;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import top.yanquithor.framework.dddbase.common.domain.model.Aggregate;
import top.yanquithor.framework.dddbase.common.domain.repository.BaseRepository;
import top.yanquithor.framework.dddbase.common.domain.service.DomainService;
import top.yanquithor.framework.dddbase.common.infrastructure.persistence.mapper.BaseMapperX;
import top.yanquithor.framework.dddbase.common.infrastructure.persistence.repository.CommonRepository;

@Slf4j
public class DomainServiceImpl<DOMAIN extends Aggregate> implements DomainService<DOMAIN> {
    
    private BaseRepository<DOMAIN> repository;
    
    public DomainServiceImpl(CommonRepository<?, DOMAIN, ? extends BaseMapperX<?>> repository) {
        this.repository = repository;
    }
    
    @Override
    public DOMAIN create(DOMAIN domain) {
        log.debug("create {} and save to database", JSON.toJSONString(domain));
        return repository.save(domain);
    }
    
    @Override
    public Long count(DOMAIN domain) {
        log.debug("query param: {}", JSON.toJSONString(domain));
        return repository.count(domain);
    }
    
    @Override
    public DOMAIN update(DOMAIN domain) {
        log.debug("update {} and save to database", JSON.toJSONString(domain));
        return repository.update(domain);
    }
    
    @Override
    public DOMAIN delete(DOMAIN domain) {
        log.debug("delete {} and save to database", JSON.toJSONString(domain));
        return repository.delete(domain);
    }
    
    @Override
    public DOMAIN getById(long id) {
        log.debug("get {} by id", id);
        return repository.getById(id);
    }
}
