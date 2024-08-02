package com.gotakeahike.takeahike.services;

import com.gotakeahike.takeahike.Exceptions.UserExistException;
import com.gotakeahike.takeahike.dto.LoginDTO;
import com.gotakeahike.takeahike.models.User;
import com.gotakeahike.takeahike.repositories.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * AuthService handles authentication and user registration operations.
 */
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;

    /**
     * Constructs an AuthService with the given dependencies.
     *
     * @param userRepository the repository for accessing user data
     * @param passwordEncoder the encoder for password encryption
     * @param authenticationManager the manager for handling authentication
     */
    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.authManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Authenticates a user and sets a JWT cookie in the response.
     *
     * @param loginDTO the data transfer object containing login credentials
     * @param res the HTTP response to add the authentication cookie to
     */
    public void login(LoginDTO loginDTO, HttpServletResponse res) {
        // Authenticate the user
        Authentication authentication = authManager.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(
                loginDTO.username(), loginDTO.password()));

        // Generate JWT token
        String token = JwtTokenProvider.generateToken(authentication);

        // Create a cookie with the JWT token
        Cookie authCookie = new Cookie("HikeCookie", token);
        authCookie.setPath("/"); // Adjust the path as needed
        res.addCookie(authCookie);
        res.setHeader("Access-Control-Allow-Credentials", "true");
    }

    /**
     * Registers a new user if the username is not already taken and the password is valid.
     *
     * @param newUser the new user to be registered
     * @throws UserExistException if the username already exists
     * @throws IllegalArgumentException if the password is less than 6 characters
     */
    public void registerUser(User newUser) throws UserExistException, IllegalArgumentException {
        // Check if the username already exists
        Boolean existingUser = userRepository.existsByUsername(newUser.getUsername());
        if (existingUser) {
            System.out.println("User exists");
            throw new UserExistException("Username already exists, please choose another one");
        }

        // Validate the password length
        if (newUser.getPassword().length() < 6) {
            throw new IllegalArgumentException("Password must be longer than 6 characters");
        }

        // Encode the password and save the new user
        User user = new User(newUser.getUsername(), passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(user);
    }
}