package top.yanquithor.framework.dddbase.common.domain.exception;

import top.yanquithor.framework.dddbase.common.domain.model.AbstractAggregate;

/**
 * 业务实体示例 - 展示如何在领域模型中使用领域异常
 *
 * @author YanQuithor
 * @version 1.1.1
 * @since 2025-12-13
 */
public class SampleAggregate extends AbstractAggregate {
    
    private String id;
    private String name;
    private int age;
    
    public SampleAggregate(String id, String name, int age) {
        setId(id);
        setName(name);
        setAge(age);
    }
    
    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new DomainValidationException("ID cannot be null or empty");
        }
        this.id = id;
    }
    
    public void setName(String name) {
        if (name == null || name.length() < 2) {
            throw new DomainValidationException("Name must be at least 2 characters long");
        }
        this.name = name;
    }
    
    public void setAge(int age) {
        if (age < 0) {
            throw new DomainValidationException("Age cannot be negative");
        }
        if (age > 150) {
            throw new DomainValidationException("Age cannot exceed 150");
        }
        this.age = age;
    }
    
    public void performBusinessOperation() {
        if (age < 18) {
            throw new BusinessRuleViolationException("User must be at least 18 years old to perform this operation");
        }
        
        // 模拟其他业务规则
        if ("admin".equals(name)) {
            throw new InvariantViolationException("Name cannot be 'admin' which is reserved");
        }
    }
    
    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
}