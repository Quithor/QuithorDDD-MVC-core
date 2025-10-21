package top.yanquithor.framework.dddbase.common.interfaces.vo;

import lombok.Getter;
import lombok.Setter;
import top.yanquithor.framework.dddbase.common.domain.model.DomainModel;

import java.util.Collection;

@Setter
@Getter
public class ResponseVO<DOMAIN extends DomainModel> {
    
    public ResponseVO(DOMAIN domain) {
    }
    
    public static <DOMAIN extends DomainModel, VO extends ResponseVO<DOMAIN>> VO of(DOMAIN domain) {
        return null;
    }
    
    public static <DOMAIN extends DomainModel, VO extends ResponseVO<DOMAIN>> VO of(DOMAIN domain, Class<VO> clazz) {
        return null;
    }
}
