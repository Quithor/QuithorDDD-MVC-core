package top.yanquithor.framework.dddbase.common.domain.service;

import top.yanquithor.framework.dddbase.common.domain.model.Aggregate;

/**
 * Domain Service Interface
 *
 * @author YanQuithor
 * @version 1.1.1
 * @since 2025-12-13
 */
public interface DomainService<DOMAIN extends Aggregate> {

    // 创建领域对象
    DOMAIN create(DOMAIN domain);

    // 计算领域对象数量
    Long count(DOMAIN domain);

    // 更新领域对象
    DOMAIN update(DOMAIN domain);

    // 删除领域对象
    DOMAIN delete(DOMAIN domain);

    // 根据ID获取领域对象
    DOMAIN getById(long id);
}
