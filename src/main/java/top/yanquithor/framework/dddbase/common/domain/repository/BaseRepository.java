package top.yanquithor.framework.dddbase.common.domain.repository;

import top.yanquithor.framework.dddbase.common.domain.model.Aggregate;

import java.util.List;
import java.util.Optional;

/**
 * Base Repository Interface - 按DDD仓储模式设计
 *
 * @author YanQuithor
 * @version 1.1.1.18
 * @since 2025-12-13
 */
public interface BaseRepository<DOMAIN extends Aggregate> {
    // 添加新领域对象到仓储
    DOMAIN add(DOMAIN domain);

    // 更新已存在的领域对象
    DOMAIN update(DOMAIN domain);

    // 从仓储中移除领域对象（软删除）
    DOMAIN remove(DOMAIN domain);

    // 从仓储中永久删除领域对象（硬删除）
    void removePermanently(DOMAIN domain);

    // 根据ID查找领域对象
    Optional<DOMAIN> findById(long id);

    // 根据ID获取领域对象（与findById类似，但抛出异常而非返回Optional）
    DOMAIN getById(long id);

    // 查找所有领域对象
    List<DOMAIN> findAll();

    // 检查具有给定ID的领域对象是否存在
    boolean exists(long id);

    // 计算仓储中的领域对象数量
    Long count();
}
