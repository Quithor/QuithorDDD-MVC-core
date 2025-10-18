package top.yanquithor.framework.dddbase.common.interfaces.controller;

import top.yanquithor.framework.dddbase.common.domain.model.DomainModel;

public interface ManagerController<DOMAIN extends DomainModel> extends
        CreateController<DOMAIN>,
        ReadController<DOMAIN>,
        UpdateController<DOMAIN>,
        DeleteController<DOMAIN> {
}
