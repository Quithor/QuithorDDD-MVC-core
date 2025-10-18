package top.yanquithor.framework.dddbase.common.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.yanquithor.framework.dddbase.common.infrastructure.persistence.dataobject.BaseDO;

public interface BaseMapperX<E extends BaseDO> extends BaseMapper<E> {
}
