package top.yanquithor.framework.dddbase.common.infrastructure.converter;

import top.yanquithor.framework.dddbase.common.domain.model.Aggregate;
import top.yanquithor.framework.dddbase.common.infrastructure.persistence.dataobject.BaseDO;

/**
 * Base Converter Interface - Converts between Domain Objects and Data Objects
 *
 * @author YanQuithor
 * @version 1.1.1
 * @since 2025-12-13
 */
public interface BaseConverter<DO extends BaseDO, DOMAIN extends Aggregate> {

     // 将领域对象转换为数据对象
     DO toDO(DOMAIN domain);

     // 将数据对象转换为领域对象
     DOMAIN toDomain(DO doo);
}
