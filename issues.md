# DDD基础框架核心待解决问题

## 高优先级问题（必须解决）

### 1. ApplicationService构造函数访问权限错误
**问题**: `ApplicationService`抽象类的构造函数为`private`，无法被子类继承扩展。
**影响**: 阻止用户实现应用程序服务，违反了抽象类的设计原则。
**修复方案**: 将构造函数改为`protected`访问权限。

> 已修复

### 2. 领域事件系统缺失
**问题**: 框架缺少领域事件的基础设施，无法实现事件驱动架构。
**影响**: 无法在聚合之间进行松耦合的通信，违背了DDD原则。
**修复方案**: 实现领域事件发布者/订阅者的基础接口和机制。

> 已完成
> 已创建 DomainEvent 接口、EventPublisher 接口、EventHandler 接口、SimpleEventPublisher 实现类、更新了 Aggregate 接口和创建了 AbstractAggregate 抽象类

### 3. 领域异常层次结构缺失
**问题**: 框架没有领域特定的异常类型或适当的错误处理机制。
**影响**: 无法区分不同类型的领域错误，导致错误处理混乱。
**修复方案**: 创建全面的领域异常层次结构，为不同的错误场景提供基础异常类型。

> 已完成
> 已创建 DomainException 基类及 DomainValidationException、BusinessRuleViolationException、EntityNotFoundException、InvariantViolationException、DomainPermissionException 等子类

## 中优先级问题（重要改进）

### 4. 复杂泛型类型约束问题
**问题**: `ApplicationService`和`DomainServiceImpl`中的泛型参数约束过于复杂且可能相互矛盾。
**影响**: 使框架难以理解和使用，增加学习成本。
**修复方案**: 简化泛型参数以使框架更直观易用。

> 已完成
> 已简化 ApplicationService 的泛型参数，移除对 DomainServiceImpl 具体实现的依赖，改用 DomainService 接口；同时简化 DomainServiceImpl 构造函数参数类型

### 5. 数据操作一致性问题
**问题**: `CommonRepository`中的`delete`方法只将记录标记为"deleted"，与其他CRUD操作语义不一致。
**影响**: 可能导致使用者对删除操作产生误解。
**修复方案**: 提供明确的删除语义，确保操作行为符合预期。

> 已完成
> 已在 BaseRepository 接口中区分软删除（delete）和硬删除（hardDelete）操作，并在 CommonRepository 中实现相应方法

### 6. 领域服务基础设施缺失
**问题**: 框架不支持协调多个聚合的复杂领域服务。
**影响**: 无法实现需要跨多个聚合的复杂业务规则。
**修复方案**: 提供领域服务的基础结构和生命周期管理。

> 已完成
> 已创建 AbstractDomainService、DomainServiceCoordinator 基础设施，并提供示例实现

### 7. 验证框架集成缺失
**问题**: 没有与领域对象集成的内置验证系统，验证逻辑需在各处重复实现。
**影响**: 代码重复，维护困难，领域不变量难以保证。
**修复方案**: 提供验证接口和默认实现，便于在领域对象中集成验证逻辑。

## 低优先级问题（框架优化）

### 8. 测试支持不足
**问题**: 框架组件缺少适当的可测试性设计。
**影响**: 难以为领域服务和应用服务创建有效的单元测试。
**修复方案**: 提供测试支持工具、Mock工厂等基础设施。

### 9. 聚合根定义过于简单
**问题**: `Aggregate`接口为空接口，缺少基本标识方法。
**影响**: 各实现可能不一致，缺少统一的聚合操作接口。
**修复方案**: 添加聚合基础标识和元数据相关方法。

### 10. Repository接口设计优化
**问题**: `BaseRepository`接口方法命名和设计可能需要按DDD最佳实践调整。
**影响**: 长期使用的可维护性和一致性。
**修复方案**: 根据DDD仓储模式最佳实践优化接口设计。
