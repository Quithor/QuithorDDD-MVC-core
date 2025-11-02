package top.yanquithor.framework.dddbase.common.interfaces.controller;

import lombok.extern.slf4j.Slf4j;
import top.yanquithor.framework.dddbase.common.application.command.Result;
import top.yanquithor.framework.dddbase.common.domain.model.DomainModel;
import top.yanquithor.framework.dddbase.common.interfaces.vo.PageRequestVO;
import top.yanquithor.framework.dddbase.common.interfaces.vo.RequestVO;
import top.yanquithor.framework.dddbase.common.interfaces.vo.ResponseVO;

import java.util.List;

@Slf4j
public class ManagerController<DOMAIN extends DomainModel> implements
        CreateController<DOMAIN>,
        ReadController<DOMAIN>,
        UpdateController<DOMAIN>,
        DeleteController<DOMAIN> {
    
    @Override
    public <REQ extends RequestVO<DOMAIN>, RES extends ResponseVO<DOMAIN>> Result<RES> create(REQ request) {
        return null;
    }
    
    @Override
    public <REQ extends RequestVO<DOMAIN>, RES extends ResponseVO<DOMAIN>> Result<RES> delete(REQ request) {
        return null;
    }
    
    @Override
    public <RES extends ResponseVO<DOMAIN>> Result<List<RES>> page(PageRequestVO requestVO) {
        return null;
    }
    
    @Override
    public <REQ extends RequestVO<DOMAIN>, RES extends ResponseVO<DOMAIN>> Result<RES> getByUd(REQ requestVO) {
        return null;
    }
    
    @Override
    public <REQ extends RequestVO<DOMAIN>, RES extends ResponseVO<DOMAIN>> Result<RES> update(REQ request) {
        return null;
    }
}
