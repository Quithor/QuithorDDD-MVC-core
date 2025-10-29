package top.yanquithor.framework.dddbase.common.infrastructure.converter;

import top.yanquithor.framework.dddbase.common.domain.model.DomainModel;
import top.yanquithor.framework.dddbase.common.infrastructure.persistence.dataobject.BaseDO;

public interface BaseConverter<DO extends BaseDO, DOMAIN extends DomainModel> {
    
    default DO toDO(DOMAIN domain) {
        return (DO) DO.builder()
                .id(domain.getId())
                .createTime(domain.getCreateTime())
                .updateTime(domain.getUpdateTime())
                .status(domain.getStatus())
                .build();
    }
    
    default DOMAIN toDomain(DO doo) {
        return (DOMAIN) DOMAIN.builder()
                .id(doo.getId())
                .createTime(doo.getCreateTime())
                .updateTime(doo.getUpdateTime())
                .status(doo.getStatus())
                .build();
    }
}
