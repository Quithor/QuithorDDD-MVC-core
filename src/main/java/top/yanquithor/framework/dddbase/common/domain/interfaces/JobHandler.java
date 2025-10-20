package top.yanquithor.framework.dddbase.common.domain.interfaces;

public interface JobHandler {
    
    String execute(String param);
    
    default String getName() {
        return getClass().getSimpleName();
    }
    
    default String getDescription() {
        return getClass().getName();
    }
}
