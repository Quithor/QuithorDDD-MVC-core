package top.yanquithor.framework.dddbase.common.infrastructure.converter;

import top.yanquithor.framework.dddbase.common.domain.model.Aggregate;
import top.yanquithor.framework.dddbase.common.infrastructure.persistence.dataobject.BaseDO;

public interface BaseConverter<DO extends BaseDO, DOMAIN extends Aggregate> {
    
     DO toDO(DOMAIN domain);
     
     DOMAIN toDomain(DO doo);
}
