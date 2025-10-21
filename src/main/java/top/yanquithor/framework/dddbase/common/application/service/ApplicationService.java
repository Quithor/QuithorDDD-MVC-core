package top.yanquithor.framework.dddbase.common.application.service;

import top.yanquithor.framework.dddbase.common.domain.model.DomainModel;
import top.yanquithor.framework.dddbase.common.domain.service.DomainService;
import top.yanquithor.framework.dddbase.common.domain.service.DomainServiceImpl;
import top.yanquithor.framework.dddbase.common.interfaces.vo.PageRequestVO;
import top.yanquithor.framework.dddbase.common.interfaces.vo.RequestVO;
import top.yanquithor.framework.dddbase.common.interfaces.vo.ResponseVO;

import java.util.List;

public abstract class ApplicationService<DOMAIN extends DomainModel, DS extends DomainServiceImpl<DOMAIN>> {

    private final DomainService<DOMAIN> domainService;
    
    private ApplicationService(DS domainService) {
        this.domainService = domainService;
    }
    
    public <Q extends RequestVO<DOMAIN>, R extends ResponseVO<DOMAIN>> R create(Q requestVo) {
        if (!requestVo.verify()) {
            throw new IllegalArgumentException("参数错误");
        }
        DOMAIN domain = domainService.create(requestVo.toDomain());
        return R.of(domain);
    }
    
    public <P extends PageRequestVO, Q extends RequestVO<DOMAIN>, R extends ResponseVO<DOMAIN>> List<R> page(P page, Q queryVO) {
        if (!queryVO.verify() || !page.verify()) {
            throw new IllegalArgumentException("参数错误");
        }
        List<DOMAIN> domain = domainService.page(page, queryVO.toDomain());
        return List.of();
    }
}
