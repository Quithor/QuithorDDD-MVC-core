package top.yanquithor.framework.dddbase.common.interfaces.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class RequestVO<DOMAIN> extends BaseVO {
    
    public abstract DOMAIN toDomain();
}
