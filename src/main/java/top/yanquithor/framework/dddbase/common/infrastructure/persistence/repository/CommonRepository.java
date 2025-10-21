package top.yanquithor.framework.dddbase.common.infrastructure.persistence.repository;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import top.yanquithor.framework.dddbase.common.domain.model.DomainModel;
import top.yanquithor.framework.dddbase.common.domain.repository.BaseRepository;
import top.yanquithor.framework.dddbase.common.infrastructure.converter.BaseConverter;
import top.yanquithor.framework.dddbase.common.infrastructure.persistence.dataobject.BaseDO;
import top.yanquithor.framework.dddbase.common.infrastructure.persistence.mapper.BaseMapperX;
import top.yanquithor.framework.dddbase.common.interfaces.vo.PageRequestVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

@Slf4j
public class CommonRepository<DO extends BaseDO, DOMAIN extends DomainModel, M extends BaseMapperX<DO>> implements BaseRepository<DOMAIN> {
    
    protected final BaseConverter<DO, DOMAIN> converter;
    protected final M mapper;
    
    protected CommonRepository(BaseConverter<DO, DOMAIN> converter, M mapper) {
        this.converter = converter;
        this.mapper = mapper;
    }
    
    @Override
    public DOMAIN save(DOMAIN domain) {
        DO aDo = converter.toDO(domain);
        int i = mapper.insert(aDo);
        if (i < 1) {
            RuntimeException insertError = new RuntimeException("insert error");
            log.error("insert error", insertError);
            throw insertError;
        }
        log.debug("insert {} to database", JSON.toJSONString(aDo));
        return converter.toDomain(aDo);
    }
    
    @Override
    public List<DOMAIN> page(PageRequestVO page, DOMAIN domain) {
        log.debug("page size: {}, page num: {}, query: {}", page.getPageSize(), page.getPageNum(), JSON.toJSONString(domain));
        Page<DO> queryPage = Page.of(page.getPageNum(), page.getPageSize());
        LambdaQueryWrapper<DO> wrapper = new LambdaQueryWrapper<>();
        wrapper.setEntity(converter.toDO(domain));
        List<DO> list = mapper.selectList(queryPage, wrapper);
        log.debug("select {} records from database", list.size());
        return list.stream()
                .map(converter::toDomain)
                .toList();
    }
    
    @Override
    public Long count(DOMAIN domain) {
        if (domain == null) {
            log.debug("count all");
            return mapper.selectCount(new LambdaQueryWrapper<DO>());
        } else {
            log.debug("count query: {}", JSON.toJSONString(domain));
            return mapper.selectCount(new LambdaQueryWrapper<DO>()
                    .setEntity(converter.toDO(domain)));
        }
    }
    
    @Override
    public DOMAIN update(DOMAIN domain) {
        if (domain != null) {
            LambdaUpdateWrapper<DO> wrapper = new LambdaUpdateWrapper<>();
            wrapper.setEntity(converter.toDO(domain));
            wrapper.eq(DO::getId, domain.getId());
            mapper.update(wrapper);
        } else {
            throw new RuntimeException("domain is null");
        }
        return domain;
    }
    
    @Override
    public DOMAIN delete(DOMAIN domain) {
        if (domain != null) {
            LambdaUpdateWrapper<DO> wrapper = new LambdaUpdateWrapper<>();
            wrapper.set(DO::getStatus, "deleted");
            wrapper.eq(DO::getId, domain.getId());
            mapper.update(wrapper);
        } else {
            throw new RuntimeException("domain is null");
        }
        return domain;
    }
    
    @Override
    public DOMAIN getById(long id) {
        return converter.toDomain(mapper.selectById(id));
    }
}
