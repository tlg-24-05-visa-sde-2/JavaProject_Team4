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
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@Order(HIGHEST_PRECEDENCE) // Ensures that the more severe cases come as a priority
public class ControllerAdvices extends ResponseEntityExceptionHandler {

    private record ExceptionDetails(String message, HttpStatus httpStatus, ZonedDateTime timestamp) {
    }

    // Generic Exceptions that would be caused by and internal error we caused
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
                INTERNAL_SERVER_ERROR,
                ZonedDateTime.now(ZoneId.of("UTC"))
        );
        return new ResponseEntity<>(exceptionDetails, INTERNAL_SERVER_ERROR);
    }

    // Custom Exception if a user already exist in our database
    @ExceptionHandler(value = UserExistException.class)
    private ResponseEntity<?> userExistException(Exception ex) {
        var exceptionDetails = new ExceptionDetails(
                ex.getMessage(),
                BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("UTC"))
        );
        return new ResponseEntity<>(exceptionDetails, BAD_REQUEST);
    }

    // If we throw an auth exception, we want to give an unauthorized status
    @ExceptionHandler(value = {AuthenticationException.class})
    private ResponseEntity<?> authenticationException(Exception e) {
        var exceptionDetails = new ExceptionDetails(
                e.getMessage(),
                UNAUTHORIZED,
                ZonedDateTime.now(ZoneId.of("UTC"))
        );
        return new ResponseEntity<>(exceptionDetails, UNAUTHORIZED);
    }

    // If we have access denied exception, we want to give the forbidden status (They obv shouldn't be there)
    @ExceptionHandler(value = {AccessDeniedException.class})
    private ResponseEntity<?> accessDenied(Exception e) {
        var exceptionDetails = new ExceptionDetails(
                e.getMessage(),
                FORBIDDEN,
                ZonedDateTime.now(ZoneId.of("UTC"))
        );
        return new ResponseEntity<>(exceptionDetails, FORBIDDEN);
    }

    // Custom Exception for if a user does not exist in the database, return 404 status
    @ExceptionHandler(value = {UserNotFoundException.class})
    private ResponseEntity<?> userNotFoundException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), NOT_FOUND);
    }

    // Custom exceptions for Handling Json, this is internal and our fault
    @ExceptionHandler(value = {JSONException.class})
    private ResponseEntity<String> jsonError(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), INTERNAL_SERVER_ERROR);
    }

    // Exception for illegal arguments, for example provided password length is to short. We want to give the bad-request status
    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}