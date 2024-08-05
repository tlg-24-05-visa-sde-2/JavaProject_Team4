package com.gotakeahike.takeahike.services;

import com.gotakeahike.takeahike.Exceptions.TrailNotFoundException;
import com.gotakeahike.takeahike.dto.TrailDTO;
import com.gotakeahike.takeahike.dto.UserDTO;
import com.gotakeahike.takeahike.models.*;
import com.gotakeahike.takeahike.repositories.TrailRepository;
import com.gotakeahike.takeahike.repositories.UserRepository;
import com.gotakeahike.takeahike.Exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link UserService}.
 * <p>
 * This class uses Mockito to mock dependencies and test various methods of the {@link UserService} class.
 * It includes tests for user retrieval, adding trails to users, and removing trails from users.
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private TrailRepository trailRepository;

    @Mock
    private TrailService trailService;

    @InjectMocks
    private UserService userService;

    /**
     * Tests the {@link UserService#saveTrailToUser(TrailDTO, Long)} method for trails with advanced experience.
     * <p>
     * This test verifies that trails with a length indicating advanced experience are correctly handled.
     *
     * @throws UserNotFoundException if the exception is thrown during the test
     */
    @Test
    void testSaveTrailToUser_AdvancedExperience() throws UserNotFoundException {
        // Arrange
        Long userId = 1L;
        TrailDTO trailDTO = new TrailDTO();
        trailDTO.setAppId(1L);
        trailDTO.setName("Trail3");
        trailDTO.setTrailLength(15.0);

        User user = new User();
        user.setId(userId);
        user.setFavoritedTrails(new ArrayList<>());

        // Capture the Trail object passed to the saveTrail method
        ArgumentCaptor<Trail> trailCaptor = ArgumentCaptor.forClass(Trail.class);

        // Mock the repository calls
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        userService.saveTrailToUser(trailDTO, userId);

        // Assert
        verify(trailService, times(1)).saveTrail(trailCaptor.capture());  // Capture the Trail object
        Trail savedTrail = trailCaptor.getValue();

        assertEquals(Experience.ADVANCED, savedTrail.getTrailExperience());
        assertTrue(user.getFavoritedTrails().contains(savedTrail));
    }

    /**
     * Tests the {@link UserService#saveTrailToUser(TrailDTO, Long)} method for trails with intermediate experience.
     * <p>
     * This test verifies that trails with a length indicating intermediate experience are correctly handled.
     *
     * @throws UserNotFoundException if the exception is thrown during the test
     */
    @Test
    void testSaveTrailToUser_IntermediateExperience() throws UserNotFoundException {
        // Arrange
        Long userId = 1L;
        TrailDTO trailDTO = new TrailDTO();
        trailDTO.setAppId(1L);
        trailDTO.setName("Trail2");
        trailDTO.setTrailLength(10.0);

        User user = new User();
        user.setId(userId);
        user.setFavoritedTrails(new ArrayList<>());

        // Capture the Trail object passed to the saveTrail method
        ArgumentCaptor<Trail> trailCaptor = ArgumentCaptor.forClass(Trail.class);

        // Mock the repository calls
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        userService.saveTrailToUser(trailDTO, userId);

        // Assert
        verify(trailService, times(1)).saveTrail(trailCaptor.capture());  // Capture the Trail object
        Trail savedTrail = trailCaptor.getValue();

        assertEquals(Experience.INTERMEDIATE, savedTrail.getTrailExperience());
        assertTrue(user.getFavoritedTrails().contains(savedTrail));
    }

    /**
     * Tests the {@link UserService#saveTrailToUser(TrailDTO, Long)} method for trails with beginner experience.
     * <p>
     * This test verifies that trails with a length indicating beginner experience are correctly handled.
     *
     * @throws UserNotFoundException if the exception is thrown during the test
     */
    @Test
    void testSaveTrailToUser_BeginnerExperience() throws UserNotFoundException {
        // Arrange
        Long userId = 1L;
        TrailDTO trailDTO = new TrailDTO();
        trailDTO.setAppId(1L);
        trailDTO.setName("Trail1");
        trailDTO.setTrailLength(5.0);

        User user = new User();
        user.setId(userId);
        user.setFavoritedTrails(new ArrayList<>());

        // Capture the Trail object passed to the saveTrail method
        ArgumentCaptor<Trail> trailCaptor = ArgumentCaptor.forClass(Trail.class);

        // Mock the repository calls
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        userService.saveTrailToUser(trailDTO, userId);

        // Assert
        verify(trailService, times(1)).saveTrail(trailCaptor.capture());  // Capture the Trail object
        Trail savedTrail = trailCaptor.getValue();

        assertEquals(Experience.BEGINNER, savedTrail.getTrailExperience());
        assertTrue(user.getFavoritedTrails().contains(savedTrail));
    }

    /**
     * Tests the {@link UserService#saveTrailToUser(TrailDTO, Long)} method for a successful trail addition.
     * <p>
     * This test verifies that a trail is correctly saved and associated with the user.
     *
     * @throws UserNotFoundException if the exception is thrown during the test
     */
    @Test
    void testSaveTrailToUser_Success() throws UserNotFoundException {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setFavoritedTrails(new ArrayList<>());

        TrailDTO trailDTO = new TrailDTO();
        trailDTO.setAppId(1L);
        trailDTO.setName("Trail Name");
        trailDTO.setTrailLength(5.0);

        // Mock the repository calls
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Capture the trail saved by the trailService
        ArgumentCaptor<Trail> trailCaptor = ArgumentCaptor.forClass(Trail.class);

        // Act
        userService.saveTrailToUser(trailDTO, userId);

        // Assert
        verify(trailService).saveTrail(trailCaptor.capture());  // Verify the call to trailService
        verify(userRepository).save(user);

        Trail savedTrail = trailCaptor.getValue();
        assertTrue(user.getFavoritedTrails().contains(savedTrail));
    }

    /**
     * Tests the {@link UserService#removeTrailFromUser(Long, Long)} method for a successful trail removal.
     * <p>
     * This test verifies that a trail is correctly removed from the user's list and deleted.
     *
     * @throws UserNotFoundException if the exception is thrown during the test
     * @throws TrailNotFoundException if the exception is thrown during the test
     */
    @Test
    void testRemoveTrailFromUser_Success() throws UserNotFoundException, TrailNotFoundException {
        // Arrange
        Long userId = 1L;
        Long trailId = 1L;

        User user = new User();
        user.setId(userId);
        Trail trail = new Trail();
        trail.setId(trailId);

        user.setFavoritedTrails(new ArrayList<>());
        user.getFavoritedTrails().add(trail);

        // Mock the repository calls
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        userService.removeTrailFromUser(trailId, userId);

        // Assert
        verify(trailService).removeTrail(trailId);  // Verify the call to trailService
        verify(userRepository).save(user);
        assertFalse(user.getFavoritedTrails().contains(trail));
    }

    /**
     * Tests the {@link UserService#getUser(Long)} method when the user exists.
     * <p>
     * This test verifies that the user is correctly retrieved and mapped to a {@link UserDTO}.
     *
     * @throws UserNotFoundException if the exception is thrown during the test
     */
    @Test
    void testGetUser_UserExists() throws UserNotFoundException {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUsername("testUser");
        user.setFavoritedTrails(new ArrayList<>());

        // Mock the repository call to return the user
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        UserDTO result = userService.getUser(userId);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("testUser", result.getUsername());
        assertEquals(user.getFavoritedTrails().size(), result.getTrails().size());
    }

    /**
     * Tests the {@link UserService#getUser(Long)} method when the user does not exist.
     * <p>
     * This test verifies that a {@link UserNotFoundException} is thrown with the correct message.
     */
    @Test
    void testGetUser_UserNotFound() {
        // Arrange
        Long userId = 1L;

        // Mock the repository call to return an empty Optional
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.getUser(userId));
    }

    /**
     * Tests the {@link UserService#saveTrailToUser(TrailDTO, Long)} method when the user is not found.
     * <p>
     * This test verifies that a {@link UserNotFoundException} is thrown with the correct message.
     */
    @Test
    void testSaveTrailToUser_UserNotFound() {
        // Arrange
        Long userId = 1L;
        TrailDTO trailDTO = new TrailDTO();
        trailDTO.setAppId(1L);
        trailDTO.setName("Trail Name");
        trailDTO.setTrailLength(5.0);

        // Mock the repository call to return an empty Optional
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () -> userService.saveTrailToUser(trailDTO, userId));
        assertEquals("Cannot find user with this ID", thrown.getMessage());
    }


    /**
     * Tests the {@link UserService#removeTrailFromUser(Long, Long)} method when the user is not found.
     * <p>
     * This test verifies that a {@link UserNotFoundException} is thrown with the correct message.
     */
    @Test
    void testRemoveTrailFromUser_UserNotFound() {
        // Arrange
        Long userId = 1L;
        Long trailId = 1L;

        // Mock the repository call to return an empty Optional
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () -> userService.removeTrailFromUser(trailId, userId));
        assertEquals("Cannot find user with this ID: 1", thrown.getMessage());
    }
}