package top.yanquithor.framework.dddbase.common.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.yanquithor.framework.dddbase.common.infrastructure.persistence.dataobject.BaseDO;

/**
 * Extended Base Mapper Interface
 *
 * @author YanQuithor
 * @version 1.1.1
 * @since 2025-12-13
 */
public interface BaseMapperX<E extends BaseDO> extends BaseMapper<E> {
}
