# Quithor DDD-MVC Framework Usage Guide

## Overview

Quithor DDD-MVC Framework is a Spring Boot-based rapid development framework that follows Domain-Driven Design (DDD) principles, providing a clear layered architecture. This framework is designed to quickly build modular Java applications.

## Quick Start

### 1. Requirements

- JDK 17+
- Maven 3.6+
- Spring Boot 3.x

### 2. Project Structure

```
src/
├── main/
│   └── java/
│       └── top/yanquithor/framework/dddbase/
│           ├── common/
│           │   ├── application/          # Application Layer
│           │   ├── domain/               # Domain Layer
│           │   ├── infrastructure/       # Infrastructure Layer
│           │   └── testing/              # Testing Support
│           └── ...
```

## Core Components Usage Guide

### 1. Domain Model

#### Creating an Aggregate Root

```java
public class User extends AbstractAggregate {
    private String name;
    private String email;

    public User(String id, String name, String email) {
        this.id = id;  // Inherited from AbstractAggregate
        this.name = name;
        this.email = email;
    }

    // Business method
    public void changeEmail(String newEmail) {
        // Validate new email
        if (newEmail == null || !newEmail.contains("@")) {
            throw new DomainValidationException("Invalid email format");
        }
        this.email = newEmail;
        
        // Add domain event
        addDomainEvent(new UserEmailChangedEvent(this.id, newEmail));
    }

    // getter and setter methods
    public String getName() { return name; }
    public String getEmail() { return email; }
}
```

#### Domain Events

The framework provides a complete event system:

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

    // getter methods
    public String getUserId() { return userId; }
    public String getNewEmail() { return newEmail; }
}
```

### 2. Domain Service

#### Creating a Domain Service

```java
public class UserService extends AbstractDomainService {
    
    public UserService(EventPublisher eventPublisher) {
        super(eventPublisher);
    }

    public User createUser(String id, String name, String email) {
        return executeDomainOperation(
            new User(id, name, email),
            user -> {
                // Validate user
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

### 3. Application Service

#### Creating an Application Service

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

### 4. Repository

The framework provides a generic repository implementation:

```java
public class UserRepository extends CommonRepository<UserDO, User, UserMapper> {
    public UserRepository(BaseConverter<UserDO, User> converter, UserMapper mapper) {
        super(converter, mapper);
    }
}
```

### 5. Validation System

The framework integrates validation functionality:

```java
public class User extends AbstractAggregate implements ValidationSupport {
    
    private String name;
    private String email;
    
    // ... other code ...

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

### 6. Testing Support

The framework provides testing support infrastructure:

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
        // Given
        String userId = "123";
        String name = "John Doe";
        String email = "john@example.com";

        // When
        User user = userService.createUser(userId, name, email);

        // Then
        assertThat(user).isNotNull();
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getEmail()).isEqualTo(email);
    }
}
```

## Configuration

### Maven Dependency

Add the framework dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>top.yanquithor.framework</groupId>
    <artifactId>ddd-base</artifactId>
    <version>1.1.1</version>
</dependency>
```

## Best Practices

1. **Domain-Driven Design**: Always start design from domain models, ensuring business logic is implemented in the domain layer
2. **Aggregate Design**: Aggregates should encapsulate business invariants and maintain consistency boundaries
3. **Event-Driven**: Use domain events for loose-coupled communication between aggregates
4. **Validation**: Implement business validation logic in aggregates and value objects
5. **Testing**: Leverage the framework's testing infrastructure to write unit tests

## Extension Features

The framework supports the following extension features:
- AI plugin extensions
- Scheduled task plugins
- Other DLC module extensions

## Troubleshooting

Common issues and solutions:

1. **Compilation errors**: Check if the framework dependency is properly included
2. **Runtime errors**: Check Spring configuration and Bean registration
3. **Test failures**: Ensure proper use of the framework's testing infrastructure

## Contributing

Contributions to the framework are welcome, following the project's coding standards:
- Use Chinese for single-line comments
- Use English for documentation comments
- Keep code concise
- Use JDK 17's new features