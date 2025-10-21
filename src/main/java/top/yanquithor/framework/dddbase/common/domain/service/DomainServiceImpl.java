package top.yanquithor.framework.dddbase.common.domain.service;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import top.yanquithor.framework.dddbase.common.domain.model.DomainModel;
import top.yanquithor.framework.dddbase.common.domain.repository.BaseRepository;
import top.yanquithor.framework.dddbase.common.infrastructure.persistence.mapper.BaseMapperX;
import top.yanquithor.framework.dddbase.common.infrastructure.persistence.repository.CommonRepository;
import top.yanquithor.framework.dddbase.common.interfaces.vo.PageRequestVO;

import java.util.List;

@Slf4j
public class DomainServiceImpl<DOMAIN extends DomainModel> implements DomainService<DOMAIN>{
    
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
    public <P extends PageRequestVO> List<DOMAIN> page(P page, DOMAIN domain) {
        log.debug("page {} and save to database", JSON.toJSONString(domain));
        return repository.page(page, domain);
    }
}
