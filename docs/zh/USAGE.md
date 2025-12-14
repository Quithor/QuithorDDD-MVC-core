# Quithor DDD-MVC Framework 使用指南

## 概述

Quithor DDD-MVC Framework 是一个基于 Spring Boot 的快速开发框架，遵循领域驱动设计（DDD）的指导原则，提供清晰的分层架构。该框架旨在快速构建模块化 Java 应用程序。

## 快速开始

### 1. 环境要求

- JDK 17+
- Maven 3.6+
- Spring Boot 3.x

### 2. 项目结构

```
src/
├── main/
│   └── java/
│       └── top/yanquithor/framework/dddbase/
│           ├── common/
│           │   ├── application/          # 应用层
│           │   ├── domain/               # 领域层
│           │   ├── infrastructure/       # 基础设施层
│           │   └── testing/              # 测试支持
│           └── ...
```

## 核心组件使用指南

### 1. 领域模型（Domain Model）

#### 创建聚合根

```java
public class User extends AbstractAggregate {
    private String name;
    private String email;

    public User(String id, String name, String email) {
        this.id = id;  // 继承自 AbstractAggregate
        this.name = name;
        this.email = email;
    }

    // 业务方法
    public void changeEmail(String newEmail) {
        // 验证新邮箱
        if (newEmail == null || !newEmail.contains("@")) {
            throw new DomainValidationException("Invalid email format");
        }
        this.email = newEmail;
        
        // 添加领域事件
        addDomainEvent(new UserEmailChangedEvent(this.id, newEmail));
    }

    // getter 和 setter 方法
    public String getName() { return name; }
    public String getEmail() { return email; }
}
```

#### 领域事件

框架提供了完整的事件系统：

```java
public class UserEmailChangedEvent implements DomainEvent {
    private final String userId;
    private final String newEmail;
    private final LocalDateTime occurredOn;

    public UserEmailChangedEvent(String userId, String newEmail) {
        this.userId = userId;
        this.newEmail = newEmail;
        this.occurredOn = LocalDateTime.now();
    }

    @Override
    public String getEventId() {
        return "user-email-changed-" + userId;
    }

    @Override
    public LocalDateTime getOccurredOn() {
        return occurredOn;
    }

    // getter 方法
    public String getUserId() { return userId; }
    public String getNewEmail() { return newEmail; }
}
```

### 2. 领域服务（Domain Service）

#### 创建领域服务

```java
public class UserService extends AbstractDomainService {
    
    public UserService(EventPublisher eventPublisher) {
        super(eventPublisher);
    }

    public User createUser(String id, String name, String email) {
        return executeDomainOperation(
            new User(id, name, email),
            user -> {
                // 验证用户
                if (user.getName().length() < 2) {
                    throw new DomainValidationException("Name must be at least 2 characters");
                }
                return user;
            }
        );
    }
    
    public User changeUserEmail(User user, String newEmail) {
        return executeDomainOperation(
            user,
            u -> {
                u.changeEmail(newEmail);
                return u;
            }
        );
    }
}
```

### 3. 应用服务（Application Service）

#### 创建应用服务

```java
public class UserApplicationService extends ApplicationService<User> {
    
    private final UserService userService;
    private final BaseRepository<User> userRepository;

    public UserApplicationService(
            DomainService<User> domainService,
            UserService userService,
            BaseRepository<User> userRepository) {
        super(domainService);
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public UserDTO createUser(CreateUserCommand command) {
        User user = userService.createUser(command.getId(), command.getName(), command.getEmail());
        User savedUser = userRepository.add(user);
        return UserDTO.from(savedUser);
    }
}
```

### 4. 仓储（Repository）

框架提供了通用仓储实现：

```java
public class UserRepository extends CommonRepository<UserDO, User, UserMapper> {
    public UserRepository(BaseConverter<UserDO, User> converter, UserMapper mapper) {
        super(converter, mapper);
    }
}
```

### 5. 验证系统

框架集成了验证功能：

```java
public class User extends AbstractAggregate implements ValidationSupport {
    
    private String name;
    private String email;
    
    // ... 其他代码 ...

    @Override
    public ValidationResult validate() {
        if (name == null || name.length() < 2) {
            return ValidationResult.failure("Name must be at least 2 characters long");
        }
        
        if (email == null || !email.contains("@")) {
            return ValidationResult.failure("Email format is invalid");
        }
        
        return ValidationResult.success();
    }
}
```

### 6. 测试支持

框架提供了测试支持基础设施：

```java
public class UserServiceTest extends BaseTestSupport {
    
    private EventPublisher mockEventPublisher;
    private UserService userService;

    @BeforeEach
    void setUp() {
        mockEventPublisher = createMockEventPublisher();
        userService = new UserService(mockEventPublisher);
    }

    @Test
    void shouldCreateUserSuccessfully() {
        // 给定
        String userId = "123";
        String name = "John Doe";
        String email = "john@example.com";

        // 当
        User user = userService.createUser(userId, name, email);

        // 那么
        assertThat(user).isNotNull();
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getEmail()).isEqualTo(email);
    }
}
```

## 配置

### Maven 依赖

在您的 `pom.xml` 中添加框架依赖：

```xml
<dependency>
    <groupId>top.yanquithor.framework</groupId>
    <artifactId>ddd-base</artifactId>
    <version>1.1.1</version>
</dependency>
```

## 最佳实践

1. **领域驱动设计**：始终从领域模型开始设计，确保业务逻辑在领域层实现
2. **聚合设计**：聚合应封装业务不变量，保持一致性边界
3. **事件驱动**：使用领域事件实现聚合间的松耦合通信
4. **验证**：在聚合和值对象中实现业务验证逻辑
5. **测试**：利用框架提供的测试基础设施编写单元测试

## 扩展功能

框架支持以下扩展功能：
- AI 插件扩展
- 定时任务插件
- 其他 DLC 模块扩展

## 故障排除

常见问题及解决方案：

1. **编译错误**：检查是否正确包含了框架依赖
2. **运行时错误**：检查 Spring 配置和 Bean 注册
3. **测试失败**：确保正确使用框架提供的测试基础设施

## 贡献

欢迎对框架进行贡献，遵循项目的编码规范：
- 使用中文单行注释
- 使用英文文档注释
- 代码简洁
- 使用 JDK 17 的新特性