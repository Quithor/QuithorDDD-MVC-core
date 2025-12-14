package top.yanquithor.framework.dddbase.common.domain.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 验证注解 - 用于在字段或方法上声明验证规则
 *
 * @author YanQuithor
 * @version 1.1.1.15
 * @since 2025-12-13
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidationRule {
    String message() default "Validation failed";
    Class<? extends ValidationRuleProcessor> processor();
}