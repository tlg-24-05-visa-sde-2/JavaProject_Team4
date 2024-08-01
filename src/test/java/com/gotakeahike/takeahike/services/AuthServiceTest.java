package com.gotakeahike.takeahike.services;


import com.gotakeahike.takeahike.repositories.UserRepository;
import com.gotakeahike.takeahike.Exceptions.UserExistException;
import com.gotakeahike.takeahike.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {}

    @Test
    void testRegisterUser_Success() throws UserExistException {
        User newUser = new User("testUser", "testPassword");

        when(userRepository.existsByUsername(newUser.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(newUser.getPassword())).thenReturn("encodedPassword");

        authService.registerUser(newUser);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUser_UserAlreadyExists() {
        User newUser = new User("testUser", "testPassword");

        when(userRepository.existsByUsername(newUser.getUsername())).thenReturn(true);

        assertThrows(UserExistException.class, () -> {
            authService.registerUser(newUser);
        });

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testRegisterUser_PasswordTooShort() {
        User newUser = new User("testUser", "short");

        assertThrows(IllegalArgumentException.class, () -> {
            authService.registerUser(newUser);
        });

        verify(userRepository, never()).save(any(User.class));
    }
}