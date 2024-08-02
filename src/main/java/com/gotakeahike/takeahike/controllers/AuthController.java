package com.gotakeahike.takeahike.controllers;

import com.gotakeahike.takeahike.Exceptions.UserExistException;
import com.gotakeahike.takeahike.dto.LoginDTO;
import com.gotakeahike.takeahike.models.User;
import com.gotakeahike.takeahike.services.AuthService;
import com.gotakeahike.takeahike.services.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

/**
 * AuthController handles HTTP requests related to authentication operations.
 * <p>
 * This class provides endpoints for:
 * - User signup
 * - User login
 * - User logout
 * - Checking if a user is logged in
 * <p>
 * This controller interacts with the AuthService to perform authentication operations.
 */
@RestController
@CrossOrigin(
        origins = {"http://localhost:3000", "http://localhost:8080"},
        allowCredentials = "true",
        exposedHeaders = "*"
)
@RequestMapping("/auth") // Everything after this will be accessed via "/auth/your-endpoint"
public class AuthController {
    // Dependency inject the AuthService
    private final AuthService authService;

    /**
     * Constructs an AuthController with the provided AuthService.
     *
     * @param authService the AuthService to handle authentication operations
     */
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * User signup route.
     * <p>
     * This endpoint handles user registration.
     *
     * @param newUser the new user to register
     * @return a ResponseEntity with a success message
     * @throws UserExistException if the username already exists
     * @throws IllegalArgumentException if the password is invalid
     */
    @PostMapping("/signup") // <-- this is "your-endpoint" from above
    private ResponseEntity<String> signup(@RequestBody User newUser) throws UserExistException, IllegalArgumentException {
        authService.registerUser(newUser);
        return ResponseEntity.status(HttpStatus.OK).body("User registered successfully"); // Returns response to the client
    }

    /**
     * User login route.
     * <p>
     * This endpoint handles user login.
     *
     * @param loginDTO the login credentials
     * @param response the HTTP response to add the authentication cookie
     * @return a ResponseEntity with a success message
     */
    @PostMapping("/login") // <-- this is "your-endpoint" from above
    private ResponseEntity<Map<String, String>> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        authService.login(loginDTO, response);                          // Use the auth service to log the user in
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Success");                         // This will return JSON to the JavaScript that looks like {"message": "Success"}
        return ResponseEntity.status(HttpStatus.OK).body(responseBody); // Returns response to the client
    }

    /**
     * User logout route.
     * <p>
     * This endpoint handles user logout.
     *
     * @return a ResponseEntity with a success message
     */
    @PostMapping("/logout") // <-- this is "your-endpoint" from above
    private ResponseEntity<?> logout() {
        return ResponseEntity.status(HttpStatus.OK).body("Logged out"); // Returns response to the client
    }

    /**
     * Validate user is logged in.
     * <p>
     * This endpoint checks if a user is logged in by validating the JWT token.
     *
     * @param jwtToken the JWT token from the cookie
     * @return a ResponseEntity with a boolean indicating if the user is logged in
     * @throws AuthenticationException if the token is invalid
     */
    @GetMapping("/isLoggedIn") // <-- this is "your-endpoint" from above
    private ResponseEntity<Boolean> isLoggedIn(@CookieValue("HikeCookie") String jwtToken) throws AuthenticationException {
        Boolean isLoggedIn = JwtTokenProvider.validateToken(jwtToken); // Call the static JwtTokenProvider method to validate the token
        return ResponseEntity.status(HttpStatus.OK).body(isLoggedIn); // Returns response to the client
    }
}