package top.yanquithor.framework.dddbase.common.interfaces.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Getter
public class PageRequestVO {
    
    private int pageNum;
    private int pageSize;
    
    public boolean verify() {
        if (pageNum == 0 || pageSize == 0) {
            NullPointerException nullPointerException = new NullPointerException("空参警告");
            log.error("page param is null", nullPointerException);
            pageNum = pageNum == 0 ? 1 : pageNum;
            pageSize = pageSize == 0 ? 10 : pageSize;
        }
        return pageNum > 0 && pageSize > 0;
    }
}
