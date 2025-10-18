package top.yanquithor.framework.dddbase.common.interfaces.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class ResponseVO<DOMAIN> {
    
    public abstract DOMAIN toDomain();
}
