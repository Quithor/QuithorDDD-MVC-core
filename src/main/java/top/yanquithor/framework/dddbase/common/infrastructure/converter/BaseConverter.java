package top.yanquithor.framework.dddbase.common.infrastructure.converter;

import top.yanquithor.framework.dddbase.common.domain.model.DomainModel;
import top.yanquithor.framework.dddbase.common.infrastructure.persistence.dataobject.BaseDO;

public interface BaseConverter<DO extends BaseDO, DOMAIN extends DomainModel> {
    
    DO toDO(DOMAIN domain);
    
    DOMAIN toDomain(DO doo);
}
