package com.gotakeahike.takeahike.controllers;

import com.gotakeahike.takeahike.Exceptions.UserExistException;
import com.gotakeahike.takeahike.dto.LoginDTO;
import com.gotakeahike.takeahike.models.User;
import com.gotakeahike.takeahike.services.AuthService;
import com.gotakeahike.takeahike.services.JwtTokenProvider;
import com.gotakeahike.takeahike.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;

/*
 * Think about this in terms of your fetch request. In here, we are creating the 'url' endpoints for your 'fetch' request
 *
 * USE "http://localhost:8080/auth" to access the routes below from your JavaScript/HTML
 */
@RestController
@RequestMapping("/auth") // everything after this will be accessed via "/auth/your-endpoint"
public class AuthController {
    // DEPENDENCY INJECT THE USER SERVICE
    private final AuthService authService;
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    // User Signup Route
    @PostMapping("/signup") // <-- this is "your-endpoint" from above
    private ResponseEntity<String> signup(@RequestBody User newUser) throws UserExistException {
        authService.registerUser(newUser);
        return ResponseEntity.ok("User registered successfully");
    }

    // User Login Route
    @PostMapping("/login") // <-- this is "your-endpoint" from above
    private ResponseEntity<?> login(@RequestBody LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response) {
        authService.login(loginDTO, request, response);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully logged in");
    }

    // User logout route, yes it does nothing, security config handles it
    // but the route must still exist
    @PostMapping("/logout")
    private ResponseEntity<?> logout() {
        return ResponseEntity.status(HttpStatus.OK).body("Logged out");
    }

    // Validate user is logged in (for purposes in React)
    @GetMapping("/isLoggedIn")
    private ResponseEntity<Boolean> isLoggedIn(@CookieValue("HikeCookie") String jwtToken) throws AuthenticationException {
        // This will validate the Json web token, if it is invalid, it will throw an Auth Exception.
        // All exceptions are 'punted' to the controller advices in the Exceptions package
        Boolean isLoggedIn = JwtTokenProvider.validateToken(jwtToken);
        return ResponseEntity.status(HttpStatus.OK).body(isLoggedIn);
    }
}