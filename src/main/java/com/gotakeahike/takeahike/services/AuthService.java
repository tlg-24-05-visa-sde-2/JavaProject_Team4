package com.gotakeahike.takeahike.services;

import com.gotakeahike.takeahike.Exceptions.UserExistException;
import com.gotakeahike.takeahike.dto.LoginDTO;
import com.gotakeahike.takeahike.models.User;
import com.gotakeahike.takeahike.repositories.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.authManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(User newUser) throws UserExistException {
        Boolean existingUser = userRepository.existsByUsername(newUser.getUsername());
        if (existingUser) {
            System.out.println("User exists");
            throw new UserExistException("Username already exists, please choose another one");
        }
        User user = new User(newUser.getUsername(), passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(user);
    }

    public void login(LoginDTO loginDTO, HttpServletRequest req, HttpServletResponse res) {
        Authentication authentication = authManager.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(
                loginDTO.getUsername(), loginDTO.getPassword()));

        String token = JwtTokenProvider.generateToken(authentication);
        Cookie authCookie = new Cookie("HikeCookie", token);
        authCookie.setPath("/"); // Adjust the path as needed
        res.addCookie(authCookie);
        res.setHeader("Access-Control-Allow-Credentials", "true");
    }
}
