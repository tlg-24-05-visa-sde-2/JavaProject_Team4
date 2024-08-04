# Understanding Model Classes in Spring

In Spring, a model class represents an entity that is mapped to a database table. It is a key part of the persistence layer in a Spring application, often managed using JPA (Java Persistence API). Here’s an overview of how a model class works and some examples to illustrate its usage.

For more details on annotations, refer to the [Annotations Cheat Sheet](../../../../../../../extra-resources/AnnotationsCheatSheet.md).

## Model Class Overview

### Annotations Used

- **`@Entity`**: Marks the class as a JPA entity that will be mapped to a database table.
- **`@Table(name = "app_user")`**: Specifies the name of the table in the database that this entity will be mapped to.
- **`@Id`**: Indicates the primary key of the entity.
- **`@GeneratedValue(strategy = GenerationType.IDENTITY)`**: Specifies the strategy for generating primary key values. `IDENTITY` means the database will handle the generation.
- **`@Column(name = "user_id")`**: Maps the field to a column in the database table and optionally specifies the column name.
- **`@Enumerated(EnumType.STRING)`**: Indicates that an enum type should be persisted as a string.
- **`@OneToMany`**: Defines a one-to-many relationship between entities. For instance, a user can have multiple favorited trails.
- **`@Getter` and `@Setter`**: Lombok annotations that automatically generate getter and setter methods for the fields.
- **`@NoArgsConstructor`**: Lombok annotation that generates a no-argument constructor for the class.

### Example Model Class

Here’s an example of a model class in Spring:

```java
@Entity // Marks this class as a JPA entity
@Table(name = "app_user") // Specifies the table name in the database
@Getter @Setter // Lombok annotations for getters and setters
@NoArgsConstructor // Lombok annotation for no-argument constructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id") // Maps to the 'user_id' column in the table
    private Long id;

    @Column(name = "username") // Maps to the 'username' column
    private String username;

    @Column // Maps to the 'password' column
    private String password;

    @Column // Maps to the 'city' column
    private String city;

    @Column // Maps to the 'state' column
    private String state;

    @Column
    @Enumerated(EnumType.STRING) // Enum is stored as a string
    private Experience experience;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Trail> favoritedTrails = new ArrayList<>();
}
```

## Explanation

- **`@Entity`**: Declares that the `User` class is a JPA entity, allowing it to be managed by JPA.
- **`@Table(name = "app_user")`**: Specifies that this entity should be mapped to the `app_user` table in the database.
- **`@Id` and `@GeneratedValue`**: Define the primary key and its generation strategy.
- **`@Column`**: Maps fields to database columns and allows for column name customization.
- **`@Enumerated(EnumType.STRING)`**: Specifies how the `Experience` enum should be persisted in the database.
- **`@OneToMany`**: Indicates a one-to-many relationship between `User` and `Trail`, where a user can have multiple favorited trails.

## Conclusion

Model classes in Spring provide a way to map Java objects to database tables, enabling CRUD operations and interactions with the persistence layer. By using annotations such as `@Entity`, `@Table`, and `@Column`, you can control how these mappings occur and manage relationships between entities.

For further reading, consult the [Spring Data JPA documentation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/).
