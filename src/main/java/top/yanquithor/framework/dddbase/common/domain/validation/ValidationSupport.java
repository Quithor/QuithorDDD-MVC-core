package top.yanquithor.framework.dddbase.common.domain.validation;

/**
 * 验证支持接口 - 提供领域对象的验证能力
 *
 * @author YanQuithor
 * @version 1.1.1.15
 * @since 2025-12-13
 */
public interface ValidationSupport {
    /**
     * 验证对象是否符合业务规则
     * @return 验证结果
     */
    ValidationResult validate();

    /**
     * 如果验证失败则抛出异常
     */
    default void validateOrFail() {
        ValidationResult result = validate();
        if (!result.isValid()) {
            String errorMessages = String.join("; ", result.getErrorMessages());
            throw new IllegalArgumentException("Validation failed: " + errorMessages);
        }
    }
}