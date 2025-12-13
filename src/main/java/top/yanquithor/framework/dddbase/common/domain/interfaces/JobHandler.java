package top.yanquithor.framework.dddbase.common.domain.interfaces;

/**
 * Job Handler Interface - Defines the contract for scheduled job execution
 *
 * @author YanQuithor
 * @version 1.1.1
 * @since 2025-12-13
 */
public interface JobHandler {

    // 执行任务
    String execute(String param);

    // 获取Cron表达式
    String getCron();

    // 任务是否激活
    boolean active();

    // 获取任务名称
    default String getName() {
        return getClass().getSimpleName();
    }

    // 获取任务描述
    default String getDescription() {
        return getClass().getName();
    }
}
