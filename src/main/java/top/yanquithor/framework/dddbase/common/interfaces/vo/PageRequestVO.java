package top.yanquithor.framework.dddbase.common.interfaces.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PageRequestVO {
    
    private int pageNum;
    private int pageSize;
}
