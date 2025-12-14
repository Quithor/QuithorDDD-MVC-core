package top.yanquithor.framework.dddbase.common.infrastructure.persistence.repository;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import top.yanquithor.framework.dddbase.common.domain.model.Aggregate;
import top.yanquithor.framework.dddbase.common.domain.repository.BaseRepository;
import top.yanquithor.framework.dddbase.common.infrastructure.converter.BaseConverter;
import top.yanquithor.framework.dddbase.common.infrastructure.persistence.dataobject.BaseDO;
import top.yanquithor.framework.dddbase.common.infrastructure.persistence.mapper.BaseMapperX;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Optional;

/**
 * Common Repository Implementation
 *
 * @author YanQuithor
 * @version 1.1.1.18
 * @since 2025-12-13
 */
@Slf4j
public class CommonRepository<DO extends BaseDO, DOMAIN extends Aggregate, M extends BaseMapperX<DO>> implements BaseRepository<DOMAIN> {

    protected final BaseConverter<DO, DOMAIN> converter;
    protected final M mapper;

    protected CommonRepository(BaseConverter<DO, DOMAIN> converter, M mapper) {
        this.converter = converter;
        this.mapper = mapper;
    }

    @Override
    public DOMAIN add(DOMAIN domain) {
        // 添加领域对象到仓储
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
    public DOMAIN update(DOMAIN domain) {
        // 更新领域对象
        if (domain != null) {
            LambdaUpdateWrapper<DO> wrapper = new LambdaUpdateWrapper<>();
            wrapper.setEntity(converter.toDO(domain));
            mapper.update(wrapper);
        } else {
            throw new RuntimeException("domain is null");
        }
        return domain;
    }

    @Override
    public DOMAIN remove(DOMAIN domain) {
        // 从仓储中移除领域对象（软删除）
        if (domain != null) {
            LambdaUpdateWrapper<DO> wrapper = new LambdaUpdateWrapper<>();
            wrapper.set(DO::getStatus, "deleted");
            mapper.update(wrapper);
        } else {
            throw new RuntimeException("domain is null");
        }
        return domain;
    }

    @Override
    public void removePermanently(DOMAIN domain) {
        // 从仓储中永久删除领域对象（硬删除）
        if (domain != null) {
            DO doDelete = converter.toDO(domain);
            mapper.deleteById(doDelete.getId());
        } else {
            throw new RuntimeException("domain is null");
        }
    }

    @Override
    public Optional<DOMAIN> findById(long id) {
        // 根据ID查找领域对象
        DOMAIN domain = converter.toDomain(mapper.selectById(id));
        return Optional.ofNullable(domain);
    }

    @Override
    public DOMAIN getById(long id) {
        // 根据ID获取领域对象
        return converter.toDomain(mapper.selectById(id));
    }

    @Override
    public List<DOMAIN> findAll() {
        // 查找所有领域对象
        List<DO> allDataObjects = mapper.selectList(new LambdaQueryWrapper<>());
        return allDataObjects.stream()
                .map(converter::toDomain)
                .toList();
    }

    @Override
    public boolean exists(long id) {
        // 检查具有给定ID的领域对象是否存在
        return mapper.selectById(id) != null;
    }

    @Override
    public Long count() {
        // 计算仓储中的领域对象数量
        return mapper.selectCount(new LambdaQueryWrapper<>());
    }
}
