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

/**
 * Unit tests for the {@link AuthService} class.
 * <p>
 * This test class verifies the behavior of the {@link AuthService} methods, specifically focusing on the
 * {@code registerUser} method. It uses Mockito for mocking dependencies and JUnit for testing.
 * </p>
 */
@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    /**
     * Initializes the test environment before each test is executed.
     * This method is currently empty but can be used to set up common test data or configurations.
     */
    @BeforeEach
    void setUp() {}

    /**
     * Tests the {@code registerUser} method of the {@link AuthService} for a successful user registration.
     * <p>
     * This test verifies that when a user does not already exist and the password is valid, the user is saved
     * in the repository. It mocks the necessary dependencies and checks that the {@code save} method of
     * {@code userRepository} is called once.
     * </p>
     *
     * @throws UserExistException if the user already exists, but this is not expected in this test.
     */
    @Test
    void testRegisterUser_Success() throws UserExistException {
        User newUser = new User("testUser", "testPassword");

        when(userRepository.existsByUsername(newUser.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(newUser.getPassword())).thenReturn("encodedPassword");

        authService.registerUser(newUser);

        verify(userRepository, times(1)).save(any(User.class));
    }

    /**
     * Tests the {@code registerUser} method of the {@link AuthService} when the user already exists.
     * <p>
     * This test verifies that when the user already exists in the repository, the method throws a
     * {@link UserExistException}. It also ensures that the {@code save} method of {@code userRepository} is never called.
     * </p>
     */
    @Test
    void testRegisterUser_UserAlreadyExists() {
        User newUser = new User("testUser", "testPassword");

        when(userRepository.existsByUsername(newUser.getUsername())).thenReturn(true);

        assertThrows(UserExistException.class, () -> {
            authService.registerUser(newUser);
        });

        verify(userRepository, never()).save(any(User.class));
    }

    /**
     * Tests the {@code registerUser} method of the {@link AuthService} when the password is too short.
     * <p>
     * This test verifies that if the provided password is shorter than the required length, an
     * {@link IllegalArgumentException} is thrown. It also ensures that the {@code save} method of
     * {@code userRepository} is never called.
     * </p>
     */
    @Test
    void testRegisterUser_PasswordTooShort() {
        User newUser = new User("testUser", "short");

        assertThrows(IllegalArgumentException.class, () -> {
            authService.registerUser(newUser);
        });

        verify(userRepository, never()).save(any(User.class));
    }
}