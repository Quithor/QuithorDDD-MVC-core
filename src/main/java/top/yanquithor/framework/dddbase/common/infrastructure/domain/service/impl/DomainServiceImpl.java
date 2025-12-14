package top.yanquithor.framework.dddbase.common.infrastructure.domain.service.impl;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import top.yanquithor.framework.dddbase.common.domain.model.Aggregate;
import top.yanquithor.framework.dddbase.common.domain.repository.BaseRepository;
import top.yanquithor.framework.dddbase.common.domain.service.DomainService;
import top.yanquithor.framework.dddbase.common.infrastructure.persistence.mapper.BaseMapperX;
import top.yanquithor.framework.dddbase.common.infrastructure.persistence.repository.CommonRepository;

/**
 * Domain Service Implementation
 *
 * @author YanQuithor
 * @version 1.1.1.11
 * @since 2025-12-13
 */
@Slf4j
public class DomainServiceImpl<DOMAIN extends Aggregate> implements DomainService<DOMAIN> {

    private BaseRepository<DOMAIN> repository;

    public DomainServiceImpl(BaseRepository<DOMAIN> repository) {
        this.repository = repository;
    }

    @Override
    public DOMAIN create(DOMAIN domain) {
        // 创建领域对象并保存到数据库
        log.debug("create {} and save to database", JSON.toJSONString(domain));
        return repository.save(domain);
    }

    @Override
    public Long count(DOMAIN domain) {
        // 查询领域对象数量
        log.debug("query param: {}", JSON.toJSONString(domain));
        return repository.count(domain);
    }

    @Override
    public DOMAIN update(DOMAIN domain) {
        // 更新领域对象
        log.debug("update {} and save to database", JSON.toJSONString(domain));
        return repository.update(domain);
    }

    @Override
    public DOMAIN delete(DOMAIN domain) {
        // 删除领域对象
        log.debug("delete {} and save to database", JSON.toJSONString(domain));
        return repository.delete(domain);
    }

    @Override
    public DOMAIN getById(long id) {
        // 根据ID获取领域对象
        log.debug("get {} by id", id);
        return repository.getById(id);
    }
}
