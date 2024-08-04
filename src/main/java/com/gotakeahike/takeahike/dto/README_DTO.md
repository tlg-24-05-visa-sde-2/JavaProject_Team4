# Data Transfer Objects (DTOs)

This document explains the purpose and usage of Data Transfer Objects (DTOs) within our application. DTOs, formerly known as value objects, are used to simplify and transfer commonly needed data across different parts of the application.

## Why Use DTOs?

DTOs serve several purposes:

- **Simplification**: They provide a simplified view of complex objects, focusing on only the necessary data.
- **Data Transfer**: They facilitate the movement of data between different layers of an application or between different applications.
- **Security**: By exposing only the necessary data, they help protect sensitive information.

For a comprehensive discussion on DTOs, including their advantages and disadvantages, refer to this [Stack Overflow conversation](https://stackoverflow.com/questions/1051182/what-is-a-data-transfer-object-dto).

For details on annotations used in the examples below, refer to the [Annotations Cheat Sheet](../../../../../../../extra-resources/AnnotationsCheatSheet.md).

## Scenarios for Using DTOs
While there are many reasons you may find yourself needing a DTO, we are only going to cover a couple of them here.

### Scenario 1: Exposing Non-Sensitive User Data

In our `models` package, we have a class called `User` that contains sensitive information such as passwords and credit card numbers. To provide only non-sensitive data to the client (e.g., JavaScript/HTML), we create a `UserDTO` class.

**Example: `User.java`**
```java
@Entity // Indicates that this class is a JPA entity
@Table // Specifies the table in the database
public class User {
    private Long id;                // Unique identifier for the user
    private String username;        // Username of the user
    private String password;        // Password (sensitive data)
    private String firstName;       // User's first name
    private Boolean accountVerified; // Indicates if the user's account is verified
    private int creditCardNumber;   // Credit card number (sensitive data)
}
```

Within this class, there is clearly some data we do not want to willingly expose to the client over an HTTP request. To handle this, we use a DTO to provide only the necessary information.

**Example: `UserDTO.java`**
```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
  private Long id;                // ID that can be safely exposed
  private String username;        // Username for display
  private Boolean accountVerified; // Status of account verification
}
```
With this DTO, we are limiting the amount of data we are giving to the client. Most request won't require a user's credit card information or password(depending on the type of app we are building).

**Example Usage: `UserService.java`**
```java
public UserDTO getUser(Long userId) {
  User user = myDatabase.getUserById(userId);

  UserDTO simpleUserData = new UserDTO(user.getId(), user.getUsername(), user.isAccountVerified());
  return simpleUserData;
}
```

### Scenario 2: Handling External API Responses
When making requests to third-party APIs (e.g., Unsplash), we need to map the response data into our application. In this scenario, we create a DTO that reflects the structure of the API response data.

**Example: `Fetching Data from Unsplash API`**
```java
public ImageDTO getImageFromUnsplashAPI() {
    String url = "https://www.unsplash.com/get/image/Grand-Canyon";
    
    // Build and send the HTTP request
    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
    HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

    // Convert the API response into ImageDTO   
    ObjectMapper mapper = new ObjectMapper();

    // Map API data into ImageDTO
    return mapper.readValue(response.body(), mapper.getTypeFactory().constructMapType(Map.class, String.class, ImageDTO.class));
}
```

Note: The ImageDTO class should match the structure of the data returned by the API. Tools like Postman are useful for inspecting API responses and designing the corresponding DTO.

Java, Spring, and the web stack handle the conversion of Java objects to JSON format automatically. However, you can customize serialization and formatting if needed.

## Conclusion
By using DTO's to handle external API responses, we simplify data processing and ensure that our application interacts with external services efficiently.