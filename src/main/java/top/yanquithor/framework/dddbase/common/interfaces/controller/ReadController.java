package top.yanquithor.framework.dddbase.common.interfaces.controller;

import top.yanquithor.framework.dddbase.common.application.command.Result;
import top.yanquithor.framework.dddbase.common.domain.model.DomainModel;
import top.yanquithor.framework.dddbase.common.interfaces.vo.PageRequestVO;
import top.yanquithor.framework.dddbase.common.interfaces.vo.RequestVO;
import top.yanquithor.framework.dddbase.common.interfaces.vo.ResponseVO;

import java.util.List;

public interface ReadController<DOMAIN extends DomainModel> {
    
    <RES extends ResponseVO<DOMAIN>> Result<List<RES>> page(PageRequestVO requestVO);
    
    <REQ extends RequestVO<DOMAIN>, RES extends ResponseVO<DOMAIN>> Result<RES> getByUd(REQ requestVO);
}
