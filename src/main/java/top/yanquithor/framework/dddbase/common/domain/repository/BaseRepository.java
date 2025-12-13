package top.yanquithor.framework.dddbase.common.domain.repository;

import top.yanquithor.framework.dddbase.common.domain.model.Aggregate;

/**
 * Base Repository Interface
 *
 * @author YanQuithor
 * @version 1.1.1
 * @since 2025-12-13
 */
public interface BaseRepository <DOMAIN extends Aggregate> {
    // 保存领域对象
    DOMAIN save(DOMAIN domain);

    // 计算领域对象数量
    Long count(DOMAIN domain);

    // 更新领域对象
    DOMAIN update(DOMAIN domain);

    // 删除领域对象
    DOMAIN delete(DOMAIN domain);

    // 根据ID获取领域对象
    DOMAIN getById(long id);
}
