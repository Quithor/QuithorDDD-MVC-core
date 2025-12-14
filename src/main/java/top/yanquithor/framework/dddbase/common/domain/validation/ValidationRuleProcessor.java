package top.yanquithor.framework.dddbase.common.domain.validation;

/**
 * 验证规则处理器接口
 *
 * @author YanQuithor
 * @version 1.1.1.15
 * @since 2025-12-13
 */
public interface ValidationRuleProcessor<T> {
    ValidationResult validate(T obj);
}