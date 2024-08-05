# Guide to Spring Annotations

## @Getter and @Setter

- These annotations from Lombok automatically generate getter and setter methods for your entities, eliminating the need
  to manually create them.

**`Example: `**

```java
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Long id;
    private String name;
}
```

## @NoArgsConstructor

- This annotation generates a no-argument constructor, useful when you need a default empty constructor.

**`Example: `**

```java
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class User {
    private Long id;
    private String name;
}
```

## @Data

- The `@Data` annotation is typically used from the Lombok library. It automatically generates common boilerplate code
  for your Java classes, such as getters, setters, `equals()`, `hashCode()`, and `toString()` methods.
- This is particularly useful for your DTOs.

**`Example: `**

```java
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
}
```

## @Component

- The `@Component` annotation is from the Spring Framework. It is used to declare a class as a Spring bean, allowing it
  to be automatically detected and managed by the Spring container.
- Spring beans annotated with `@Component` are typically used for services, repositories, controllers, and other
  Spring-managed components.

**`Example: `**

```java
import org.springframework.stereotype.Component;

@Component
public class UserRepository {
    public User findById(Long id) {
        // logic to find a user by id
    }
}
```

## @Service

- The `@Service` annotation is used to mark a class as a service. It indicates that the class provides some business
  functionalities and allows Spring to manage it as a service component.

**`Example: `**

```java
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public User getUserById(Long id) {
        // logic to get a user by id
    }
}
```

## JPA Annotations

- `@Entity:`
    - This annotation is used to mark a Java class as a JPA entity. An entity represents a table in a relational
      database. Each instance of the entity corresponds to a row in the table.
      **`Example: `**

```java
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
  @Id
  private Long id;
  private String name;
}
```

- `@Table:`
    - This annotation is used to specify the details of the database table to which the entity is mapped. It allows
      you to customize the table name, schema, and other properties.
  
    **`Example: `**
```java
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
  @Id
  private Long id;
  private String name;
}

```
- **`Example: Combining Annotations for a Database Model: `**
```java
@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    private Long id;

    @Column(name = "user_name")
    private String name;
}
```
- Using these annotations is crucial for ORM (Object-Relational Mapping) frameworks like Hibernate (which is often used
  with JPA). These annotations help in defining the mapping between Java objects and database tables, allowing
  developers to interact with databases using Java objects rather than writing raw SQL queries.

## `@OneToMany` and `@ManyToOne`
  - The `@OneToMany` and `@ManyToOne` annotations are used to define relationships between entities in JPA.

### `@OneToMany`

The `@OneToMany` annotation is used to define a one-to-many relationship between two entities. This relationship implies that one entity can be related to multiple instances of another entity.

#### Example

Consider a `Department` entity that can have many `Employee` entities.

```java
@Entity
public class Department {
  @Id
  private Long id;
  private String name;

  @OneToMany(mappedBy = "department")
  private List<Employee> employees;

  // getters and setters
}
```

### `@ManyToOne`
- The @ManyToOne annotation is used to define a many-to-one relationship between two entities. This relationship implies that multiple instances of one entity can be related to a single instance of another entity.

#### Example
Continuing from the previous example, an Employee entity belongs to one Department.

```java
@Entity
public class Employee {
  @Id
  private Long id;
  private String name;

  @ManyToOne
  private Department department;

  // getters and setters
}
```

### Combined Example
Combining both annotations, we can see the relationship between Department and Employee.

```java
@Entity
public class Department {
  @Id
  private Long id;
  private String name;

  @OneToMany(mappedBy = "department")
  private List<Employee> employees;

  // getters and setters
}

@Entity
public class Employee {
  @Id
  private Long id;
  private String name;

  @ManyToOne
  private Department department;

  // getters and setters
}
```

## @JsonIgnoreProperties
- Avoiding Infinite Recursion in Bidirectional Relationships:
  If you have bidirectional relationships between classes, and you are serializing the objects to JSON, you may
  encounter issues with infinite recursion. For example, consider two classes Parent and Child where Parent has a list
  of Child objects, and each Child has a reference to its Parent. In such cases, adding @JsonIgnoreProperties on one
  side (e.g., the Child class) can break the recursion.

  **`Example: `**
```java
@Entity 
@JsonIgnoreProperties("parent")
public class Child {
    // fields, constructors, and methods
    @ManyToOne
    private Parent parent;
}
```
- Ignoring Unknown Properties During Deserialization:
  If your JSON data might contain additional properties that are not present in your Java class, you can use
  @JsonIgnoreProperties to ignore those unknown properties during deserialization.

  **`Example: `**
```java
@JsonIgnoreProperties(ignoreUnknown = true)
public class MyDto {
    // fields, constructors, and methods
}
```

- Ignoring Specific Properties:
  You may want to exclude certain properties from being included in the JSON output. This can be useful when you have
  properties in your Java class that are not relevant or should not be exposed in certain contexts.
- 
**`Example: `**
```java
@JsonIgnoreProperties({"internalId", "secretKey"})
public class MySensitiveData {
    // fields, constructors, and methods
}
```

## @Transactional

The `@Transactional` annotation in Spring is essential for managing transaction boundaries and ensuring data consistency. Below are the key aspects and examples of how to use `@Transactional`:

### Transaction Propagation

`@Transactional` supports various propagation behaviors, which define how the transaction interacts with existing transactions. For example, whether the method should join an existing transaction or start a new one. The `propagation` attribute allows you to specify these behaviors.

```java
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.REQUIRED)
public void performOperation() {
    // method implementation
}
```

### Isolation Level
The isolation attribute of `@Transactional` allows you to specify the isolation level for the transaction. Isolation levels define the degree to which one transaction must be isolated from the effects of other concurrent transactions.

```java
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
public void performOperation() throws Exception {
    // method implementation
}
```

### Read-only Transactions
You can use the readOnly attribute to indicate that a transaction is read-only. This can be beneficial for performance optimization because read-only transactions might be able to acquire fewer locks.

```java
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public void fetchData() {
    // method implementation
}
```

### Exception Handling
By default, a transaction is marked for rollback only for unchecked exceptions. If a checked exception is thrown and you want it to trigger a rollback, you need to use the rollbackFor attribute.

```java
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
public void performOperation() throws Exception {
    // method implementation
}
```

### Use Case Example
The @Transactional annotation is extremely useful for multiple interactions with the database. For example, in a scenario where a user details request involves fetching a lot of data from the database, and you do not want to fetch it eagerly, using @Transactional ensures that all database operations within the method are handled in a single transaction.

```java
import org.springframework.transaction.annotation.Transactional;

@Transactional
public UserDTO getUserAndTrails(Long userId) {
        // multiple database interactions
        UserDTO userDetails = userRepository.findById(userId);
        // more interactions, e.g. find userTrails and add them to the user
        return userDetails;
}
```

## @Bean Annotation

The `@Bean` annotation in Spring is used to indicate that a method produces a bean to be managed by the Spring container. It is typically used in a `@Configuration` class to define beans that are created and managed by Spring's Inversion of Control (IoC) container.

### Usage

The `@Bean` annotation is used within a method to tell Spring that the returned object should be registered as a bean.

### Example

Consider a simple example where we define a `DataSource` bean in a configuration class:

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/mydb");
        dataSource.setUsername("user");
        dataSource.setPassword("password");
        return dataSource;
    }
}
```

In this example:

- The `@Configuration` annotation indicates that the class contains one or more `@Bean` methods.
- The `dataSource` method is annotated with `@Bean`, so Spring will call this method and register the returned `DataSource` object as a bean in the application context.

### Benefits

- **Encapsulation**: `@Bean` methods allow you to encapsulate the bean creation logic and manage it centrally within a configuration class.
- **Customization**: You can customize bean creation, initialization, and configuration within the `@Bean` method.
- **Dependencies**: `@Bean` methods can have arguments, which Spring will automatically resolve by looking up other beans in the application context.

### Example with Dependencies

Consider a scenario where a `Service` bean depends on a `Repository` bean:

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Repository repository() {
        return new Repository();
    }

    @Bean
    public Service service() {
        return new Service(repository());
    }
}
```
In this example:

- The service bean depends on the repository bean.
- Spring will resolve the dependency and pass the repository bean to the service bean's constructor.

### Lazy Initialization
You can specify that a bean should be lazily initialized by using the @Lazy annotation or the lazy attribute of the @Bean annotation:

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class LazyConfig {

  @Bean
  @Lazy
  public ExpensiveBean expensiveBean() {
    return new ExpensiveBean();
  }
}
```

In this example 
- The expensiveBean will be created only when it is first requested, rather than at application startup.

- The @Bean annotation provides a flexible way to define and configure beans in a Spring application, enabling fine-grained control over bean creation and dependency management.
## @Component Annotation

The `@Component` annotation in Spring is used to indicate that a class is a Spring-managed component. This means that Spring will automatically detect and register the class as a bean in the application context. It is a generic stereotype for any Spring-managed component.

### Usage

The `@Component` annotation can be used on any class to mark it as a Spring bean.

### Example

Consider a simple example where we define a `UserRepository` class as a Spring component:

```java
import org.springframework.stereotype.Component;

@Component
public class UserRepository {

    public User findById(Long id) {
        // logic to find a user by id
        return new User(id, "John Doe");
    }
}
```

In this example:

- The `@Component` annotation tells Spring to treat the `UserRepository` class as a bean and manage its lifecycle.
- Spring will automatically scan for this class and register it in the application context.

### Stereotype Annotations

Spring provides several specialized stereotype annotations that are derived from `@Component`:

- **`@Service`**: Used to annotate service classes.
- **`@Repository`**: Used to annotate repository (DAO) classes.
- **`@Controller`**: Used to annotate controller classes in Spring MVC.

These annotations are functionally equivalent to `@Component` but provide better readability and semantic meaning.

### Benefits

- **Automatic Detection**: Spring automatically detects and registers classes annotated with `@Component` during component scanning.
- **Dependency Injection**: Enables dependency injection for the annotated class, allowing Spring to manage dependencies automatically.
- **Separation of Concerns**: Encourages a clear separation of concerns by marking different layers of an application with specific stereotype annotations (`@Service`, `@Repository`, `@Controller`).