package com.gotakeahike.takeahike.Exceptions;

import org.json.JSONException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

/**
 * Global exception handler for the application.
 * <p>
 * This class uses {@code @ControllerAdvice} to handle exceptions thrown by controllers
 * and provide a centralized exception handling mechanism. It ensures consistent error responses
 * across the application.
 * </p>
 */
@ControllerAdvice
@Order(HIGHEST_PRECEDENCE) // Ensures that this advice is given the highest priority
public class ControllerAdvices extends ResponseEntityExceptionHandler {

    /**
     * Record to hold exception details.
     *
     * @param message   the error message
     * @param httpStatus the HTTP status code
     * @param timestamp  the timestamp of when the exception occurred
     */
    private record ExceptionDetails(String message, HttpStatus httpStatus, ZonedDateTime timestamp) {
    }

    /**
     * Handles generic exceptions and runtime exceptions.
     *
     * @param ex the exception that was thrown
     * @return a {@code ResponseEntity} containing the exception details and HTTP status 500 (Internal Server Error)
     */
    @ExceptionHandler(value = {
            Exception.class,
            RuntimeException.class,
            UnsupportedOperationException.class,
            IllegalStateException.class,
            IOException.class
    })
    private ResponseEntity<?> runTimeException(Exception ex) {
        var exceptionDetails = new ExceptionDetails(
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                ZonedDateTime.now(ZoneId.of("UTC"))
        );
        return new ResponseEntity<>(exceptionDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles exceptions where a user already exists.
     *
     * @param ex the exception that was thrown
     * @return a {@code ResponseEntity} containing the exception details and HTTP status 400 (Bad Request)
     */
    @ExceptionHandler(value = UserExistException.class)
    private ResponseEntity<?> userExistException(Exception ex) {
        var exceptionDetails = new ExceptionDetails(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("UTC"))
        );
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles authentication exceptions.
     *
     * @param e the exception that was thrown
     * @return a {@code ResponseEntity} containing the exception details and HTTP status 401 (Unauthorized)
     */
    @ExceptionHandler(value = AuthenticationException.class)
    private ResponseEntity<?> authenticationException(Exception e) {
        var exceptionDetails = new ExceptionDetails(
                e.getMessage(),
                HttpStatus.UNAUTHORIZED,
                ZonedDateTime.now(ZoneId.of("UTC"))
        );
        return new ResponseEntity<>(exceptionDetails, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles access denied exceptions.
     *
     * @param e the exception that was thrown
     * @return a {@code ResponseEntity} containing the exception details and HTTP status 403 (Forbidden)
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    private ResponseEntity<?> accessDenied(Exception e) {
        var exceptionDetails = new ExceptionDetails(
                e.getMessage(),
                HttpStatus.FORBIDDEN,
                ZonedDateTime.now(ZoneId.of("UTC"))
        );
        return new ResponseEntity<>(exceptionDetails, HttpStatus.FORBIDDEN);
    }

    /**
     * Handles exceptions where a user is not found.
     *
     * @param ex the exception that was thrown
     * @return a {@code ResponseEntity} containing the exception message and HTTP status 404 (Not Found)
     */
    @ExceptionHandler(value = UserNotFoundException.class)
    private ResponseEntity<?> userNotFoundException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles JSON exceptions that occur due to internal faults.
     *
     * @param ex the exception that was thrown
     * @return a {@code ResponseEntity} containing the exception message and HTTP status 500 (Internal Server Error)
     */
    @ExceptionHandler(value = JSONException.class)
    private ResponseEntity<String> jsonError(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles illegal argument exceptions.
     *
     * @param ex the exception that was thrown
     * @return a {@code ResponseEntity} containing the exception message and HTTP status 400 (Bad Request)
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}