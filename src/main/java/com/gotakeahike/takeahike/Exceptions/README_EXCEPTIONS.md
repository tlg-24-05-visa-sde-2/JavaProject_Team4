# Understanding `@ControllerAdvice` in Spring

In Spring Framework, `@ControllerAdvice` is used to handle exceptions thrown by controllers across the entire application in a centralized manner. It allows you to manage exception handling globally, making it easier to keep your controller code clean and focused on processing requests.

For more details on annotations, refer to the [Annotations Cheat Sheet](../../../../../../../extra-resources/AnnotationsCheatSheet.md).

## What is `@ControllerAdvice`?

`@ControllerAdvice` is a specialization of the `@Component` annotation that allows you to define a global exception handler and apply common configurations across multiple controllers. It works by intercepting exceptions thrown by controllers and providing a consistent way to handle them.

### Key Features

- **Global Exception Handling**: Handle exceptions thrown by any controller in a centralized place.
- **Custom Error Responses**: Customize error responses based on different exceptions.
- **Separation of Concerns**: Keep your controllers focused on their primary responsibilities and delegate error handling to the advice.

## How It Works

When an exception occurs in a controller method, `@ControllerAdvice` detects it and invokes the corresponding method annotated with `@ExceptionHandler`. This method processes the exception and generates an appropriate response.

### Key Annotations

- `@ExceptionHandler`: Specifies the types of exceptions that the annotated method will handle.
- `@Order`: Defines the order of execution for multiple `@ControllerAdvice` instances. Lower values indicate higher precedence.

## Example

Here’s a simple example of a `@ControllerAdvice` class that handles various exceptions globally:

```java
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE) // Ensures that this advice has the highest priority
public class ControllerAdvices extends ResponseEntityExceptionHandler {

    // Inner class to encapsulate exception details
    private record ExceptionDetails(String message, HttpStatus httpStatus, ZonedDateTime timestamp) {
    }

    // Generic exception handler for various types of exceptions
    @ExceptionHandler(value = {
            Exception.class,
            RuntimeException.class,
            UnsupportedOperationException.class,
            IllegalStateException.class,
            IOException.class
    })
    private ResponseEntity<?> handleRuntimeException(Exception ex) {
        // Create an exception details object
        var exceptionDetails = new ExceptionDetails(
                ex.getMessage(),                // Exception message
                HttpStatus.INTERNAL_SERVER_ERROR, // HTTP status code
                ZonedDateTime.now(ZoneId.of("UTC")) // Timestamp of the exception
        );
        // Return a response entity with exception details
        return new ResponseEntity<>(exceptionDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```

### Explanation:

- **`@ControllerAdvice`**: Declares that this class provides global exception handling.
- **`@Order(Ordered.HIGHEST_PRECEDENCE)`**: Ensures that this advice is given the highest priority when multiple advices are used.
- **`@ExceptionHandler`**: Defines a method to handle various exceptions. The method creates a response entity with detailed exception information and returns it to the client.

This setup ensures that any exceptions thrown in your controllers are caught by `ControllerAdvices`, and a consistent error response is provided to the client.

## Adding Custom Exceptions 
Need a custom exception? No problem. All you need to do is stub out a custom Exception class.

### Example:
```java
public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }
}
```

Now we just have to add it to the controller advice and tell it what we want it to do.
```java
@ExceptionHandler(value = {UserNotFoundException.class})
    private ResponseEntity<?> userNotFoundException(Exception ex) {
    return new ResponseEntity<>(ex.getMessage(), NOT_FOUND);
}
```

### Explanation:
- **`@ExceptionHandler(value={UserNotFoundException.class})`**: If you hear my custom exception, do something.
- **`ResponseEntity<>(ex.getMessage(), NOT_FOUND)`**: Return a HTTP Response to the user with the message I gave my exception, and a Status of 404 or NOT_FOUND

## Conclusion

Using `@ControllerAdvice` for exception handling in Spring helps manage errors in a consistent and centralized way. It separates error handling logic from your controllers, allowing them to focus on their core responsibilities while providing a uniform approach to handling exceptions across your application.

For more detailed information, refer to the [Spring Framework Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-exception-handling).