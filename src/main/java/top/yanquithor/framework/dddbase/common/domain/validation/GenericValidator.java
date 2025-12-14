package top.yanquithor.framework.dddbase.common.domain.validation;

import top.yanquithor.framework.dddbase.common.domain.model.Aggregate;

import java.util.List;
import java.util.function.Predicate;

/**
 * 通用领域验证器实现
 *
 * @param <T> 要验证的对象类型
 * @author YanQuithor
 * @version 1.1.1.15
 * @since 2025-12-13
 */
public class GenericValidator<T> implements Validator<T> {
    private final List<ValidationRule<T>> rules;

    public GenericValidator(List<ValidationRule<T>> rules) {
        this.rules = rules;
    }

    @Override
    public ValidationResult validate(T obj) {
        if (rules == null || rules.isEmpty()) {
            return ValidationResult.success();
        }

        var errorMessages = new java.util.ArrayList<String>();

        for (ValidationRule<T> rule : rules) {
            ValidationResult result = rule.validate(obj);
            if (!result.isValid()) {
                errorMessages.addAll(result.getErrorMessages());
            }
        }

        if (errorMessages.isEmpty()) {
            return ValidationResult.success();
        } else {
            return ValidationResult.failure(errorMessages);
        }
    }

    /**
     * 验证规则接口
     */
    public interface ValidationRule<T> {
        ValidationResult validate(T obj);
    }

    /**
     * 创建基于断言的验证规则
     */
    public static <T> ValidationRule<T> createRule(Predicate<T> condition, String errorMessage) {
        return obj -> {
            if (condition.test(obj)) {
                return ValidationResult.success();
            } else {
                return ValidationResult.failure(errorMessage);
            }
        };
    }
}