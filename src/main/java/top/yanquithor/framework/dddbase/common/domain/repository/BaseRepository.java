package top.yanquithor.framework.dddbase.common.domain.repository;

import top.yanquithor.framework.dddbase.common.domain.model.DomainModel;
import top.yanquithor.framework.dddbase.common.interfaces.vo.PageRequestVO;

import java.util.List;

public interface BaseRepository <DOMAIN extends DomainModel> {
    DOMAIN save(DOMAIN domain);
    
    List<DOMAIN> page(PageRequestVO page, DOMAIN domain);
    
    Long count(DOMAIN domain);
    
    DOMAIN update(DOMAIN domain);
    
    DOMAIN delete(DOMAIN domain);
    
    DOMAIN getById(long id);
}
