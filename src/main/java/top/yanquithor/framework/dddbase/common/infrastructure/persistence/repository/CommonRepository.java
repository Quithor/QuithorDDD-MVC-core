package top.yanquithor.framework.dddbase.common.infrastructure.persistence.repository;

import lombok.Getter;
import top.yanquithor.framework.dddbase.common.domain.model.DomainModel;
import top.yanquithor.framework.dddbase.common.domain.repository.BaseRepository;
import top.yanquithor.framework.dddbase.common.infrastructure.converter.BaseConverter;
import top.yanquithor.framework.dddbase.common.infrastructure.persistence.dataobject.BaseDO;
import top.yanquithor.framework.dddbase.common.infrastructure.persistence.mapper.BaseMapperX;

@Getter
public class CommonRepository <DO extends BaseDO, DOMAIN extends DomainModel, M extends BaseMapperX<DO>> implements BaseRepository<DOMAIN> {
    
    protected final BaseConverter<DO, DOMAIN> converter;
    protected final M mapper;
    
    protected CommonRepository(BaseConverter<DO, DOMAIN> converter, M mapper) {
        this.converter = converter;
        this.mapper = mapper;
    }
}
