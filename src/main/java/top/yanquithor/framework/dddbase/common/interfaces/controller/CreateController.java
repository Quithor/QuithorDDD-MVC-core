package top.yanquithor.framework.dddbase.common.interfaces.controller;

import top.yanquithor.framework.dddbase.common.application.command.Result;
import top.yanquithor.framework.dddbase.common.domain.model.DomainModel;
import top.yanquithor.framework.dddbase.common.interfaces.vo.RequestVO;
import top.yanquithor.framework.dddbase.common.interfaces.vo.ResponseVO;

public interface CreateController<DOMAIN extends DomainModel> {
    
    <REQ extends RequestVO<DOMAIN>, RES extends ResponseVO<DOMAIN>> Result<RES> create(REQ request);
}
