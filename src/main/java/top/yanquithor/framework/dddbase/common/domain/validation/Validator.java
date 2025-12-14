package top.yanquithor.framework.dddbase.common.domain.validation;

import top.yanquithor.framework.dddbase.common.domain.model.Aggregate;

/**
 * 验证器接口 - 用于验证领域对象
 *
 * @param <T> 要验证的对象类型
 * @author YanQuithor
 * @version 1.1.1.15
 * @since 2025-12-13
 */
public interface Validator<T> {
    /**
     * 验证对象是否符合业务规则
     * @param obj 要验证的对象
     * @return 验证结果
     */
    ValidationResult validate(T obj);
}