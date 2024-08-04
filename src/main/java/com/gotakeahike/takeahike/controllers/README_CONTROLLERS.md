# Spring Boot Controller Overview

In a Spring Boot application, a controller is a key component of the web layer that handles HTTP requests, processes user input, and returns appropriate responses. Controllers are annotated with `@RestController` and are responsible for managing requests to specific endpoints, interacting with services, and returning responses in various formats, such as JSON or HTML.

## How Controllers Work

- **Annotation**: Controllers are marked with the `@RestController` annotation, which combines `@Controller` and `@ResponseBody`. This tells Spring that the class should handle web requests and the return values of methods should be written directly to the HTTP response body.

- **Request Mapping**: The `@RequestMapping` annotation specifies the base URL path for the controller. It can be applied at the class level to define the base path or at the method level to define specific endpoints.

- **Handling Requests**: Methods within the controller are mapped to HTTP requests using annotations such as `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`, etc. These annotations define the type of HTTP request the method will handle.

- **Dependency Injection**: Controllers often depend on services to handle business logic. Spring provides these services through dependency injection, allowing controllers to use them for processing requests.

- **Response Entities**: Controllers return `ResponseEntity` objects, which allow for more control over the HTTP response, including status codes, headers, and body content.

## Example: `UserController`

The `UserController` class is a Spring Boot controller that manages user-related operations. Here’s a breakdown of its functionality:

### Annotations

- `@RestController`: Indicates that this class is a controller and its methods return JSON responses.
- `@CrossOrigin`: Configures cross-origin resource sharing (CORS) to allow requests from specified origins.
- `@RequestMapping("/user")`: Specifies that all endpoints in this controller will be prefixed with `/user`.

### Fields and Constructor

- **Field**:
    - `private final UserService userService;`: A service used to handle user-related operations.

- **Constructor**:
    - `@Autowired`: Marks the constructor for dependency injection. Spring will automatically inject the `UserService` instance.

### Methods

**Example Method: `UserController.java`**

```java
@GetMapping("/getUser")
private ResponseEntity<UserDTO> getUser(@CookieValue("HikeCookie") String jwtToken) throws Exception {
    // Validate the JWT token and extract the user ID
    JwtTokenProvider.validateToken(jwtToken);
    Long userId = JwtTokenProvider.extractUserId(jwtToken);

    // Retrieve the user information from the UserService
    UserDTO user = userService.getUser(userId);

    // Return the user information with HTTP status 200 (OK)
    return ResponseEntity.status(HttpStatus.OK).body(user);
}
```

- **Purpose**: Retrieves user information based on a JWT token.

- **Parameters**:
  - `@CookieValue("HikeCookie") String jwtToken`: Extracts the JWT token from cookies.

- **Returns**: A `ResponseEntity` containing a `UserDTO` with HTTP status 200 (OK).

- **Exceptions**: Throws an exception if token validation or user retrieval fails.

## In case it didn't stick
Think about this in terms of making a fetch request from your JavaScript. We wanted to get a picture from unsplash to add to our vacation destination wishlist. We had to write a fetch request to "http://unsplash.com/getImage/grand-canyon".

We are making the "/getImage" all the way to the "/grand-canyon" portion. We are the API back here in Java.

**Example: `Fetch from JavaScript`**
```javascript
async function fetchYourJavaProject() {
  /*
   * The first part of the url, 'http://localhost:8080' would be your domain name wherever
   * you deploy this app and or purchase the name from "go daddy" or what have you
   */
    const url = "http://localhost:8080/YOU/ARE/CREATING/THESE/AND/THEY/GIVE/THE/DATA"; 
    const response = await fetch(url); // <- we just got data from your Java project
    console.log(resposne);             // If we did it right, you should see your database stuff
}
```

## Summary
Controllers in Spring Boot handle HTTP requests and return responses. They interact with services to process business logic and often manage the transformation of data into appropriate formats for the client. The `UserController` is an example of how controllers manage user-specific operations, demonstrating the use of dependency injection, request mapping, and response handling.

For more information on controllers in Spring Boot, refer to the [Spring Framework Documentation](https://spring.io/projects/spring-framework).