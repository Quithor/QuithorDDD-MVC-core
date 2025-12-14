package top.yanquithor.framework.dddbase.common.domain.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 验证结果 - 包含验证是否通过以及错误信息
 *
 * @author YanQuithor
 * @version 1.1.1.15
 * @since 2025-12-13
 */
public class ValidationResult {
    private final boolean isValid;
    private final List<String> errorMessages;

    private ValidationResult(boolean isValid, List<String> errorMessages) {
        this.isValid = isValid;
        this.errorMessages = errorMessages != null ? new ArrayList<>(errorMessages) : new ArrayList<>();
    }

    public static ValidationResult success() {
        return new ValidationResult(true, Collections.emptyList());
    }

    public static ValidationResult failure(String errorMessage) {
        List<String> errors = new ArrayList<>();
        errors.add(errorMessage);
        return new ValidationResult(false, errors);
    }

    public static ValidationResult failure(List<String> errorMessages) {
        return new ValidationResult(false, errorMessages);
    }

    public boolean isValid() {
        return isValid;
    }

    public List<String> getErrorMessages() {
        return Collections.unmodifiableList(errorMessages);
    }
}