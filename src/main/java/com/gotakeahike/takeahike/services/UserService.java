package com.gotakeahike.takeahike.services;

import com.gotakeahike.takeahike.Exceptions.UserExistException;
import com.gotakeahike.takeahike.Exceptions.UserNotFoundException;
import com.gotakeahike.takeahike.dto.LoginDTO;
import com.gotakeahike.takeahike.dto.TrailAPIDTO;
import com.gotakeahike.takeahike.dto.TrailDTO;
import com.gotakeahike.takeahike.dto.UserDTO;
import com.gotakeahike.takeahike.models.Experience;
import com.gotakeahike.takeahike.repositories.TrailRepository;
import com.gotakeahike.takeahike.models.Trail;
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
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final TrailRepository trailRepository;


    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TrailRepository trailRepository) {
        this.authManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.trailRepository = trailRepository;
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

    public UserDTO getUser(Long userId) throws UserNotFoundException {
        User user = userRepository.getById(userId);
        if(user == null) {
            throw new UserNotFoundException("Cannot find user with this ID");
        }

        UserDTO userData = new UserDTO(user.getId(), user.getUsername(), user.getFavoritedTrails());

        System.out.println(user);

        return userData;
    }

    public void saveTrailToUser(TrailDTO trailDTO, Long userId) throws UserNotFoundException {
        // Retrieve the user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Cannot find user with this ID"));

        // Set Experience(Enum)
        Experience experience;
        if(trailDTO.getTrailLength()<6){
            experience = Experience.BEGINNER;
        } else if (trailDTO.getTrailLength()<12) {
            experience = Experience.INTERMEDIATE;
        } else {
            experience = Experience.ADVANCED;
        }

        System.out.println("trail Length " + trailDTO.getTrailLength());

       // Create and save the new Trail
        Trail trail = new Trail();
        trail.setAppId(trailDTO.getAppId());
        trail.setTrailName(trailDTO.getName());
        trail.setTrailLength(trailDTO.getTrailLength());
        trail.setTrailExperience(experience);

        trail.setUser(user);

        Trail savedTrail = trailRepository.save(trail);

        // Add the new trail to the user's favorite trails
        user.getFavoritedTrails().add(savedTrail);

        // Save the updated user
        userRepository.save(user);
    }

    

}