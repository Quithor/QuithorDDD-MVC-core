package top.yanquithor.framework.dddbase.common.domain.service;

import top.yanquithor.framework.dddbase.common.domain.model.DomainModel;
import top.yanquithor.framework.dddbase.common.interfaces.vo.PageRequestVO;

import java.util.List;

public interface DomainService<DOMAIN extends DomainModel> {
    DOMAIN create(DOMAIN domain);
    
    <P extends PageRequestVO> List<DOMAIN> page(P page, DOMAIN domain);
}
