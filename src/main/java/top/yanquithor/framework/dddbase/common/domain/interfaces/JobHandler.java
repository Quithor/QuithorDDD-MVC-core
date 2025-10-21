package top.yanquithor.framework.dddbase.common.domain.interfaces;

public interface JobHandler {
    
    String execute(String param);
    
    String getCron();
    
    boolean active();
    
    default String getName() {
        return getClass().getSimpleName();
    }
    
    default String getDescription() {
        return getClass().getName();
    }
}
