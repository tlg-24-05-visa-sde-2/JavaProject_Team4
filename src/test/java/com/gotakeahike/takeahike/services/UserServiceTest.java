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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private TrailRepository trailRepository;
    @InjectMocks
    private UserService userService;

    @Test
    void testGetUser_UserExists() throws UserNotFoundException {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUsername("testUser");
        user.setFavoritedTrails(new ArrayList<>());  // Assume this is a List<Trail>

        // Mock the repository call
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        UserDTO result = userService.getUser(userId);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("testUser", result.getUsername());
        // Ensure that the trails are converted correctly
        assertEquals(user.getFavoritedTrails().size(), result.getTrails().size());
    }

    @Test
    void testGetUser_UserNotFound() {
        // Arrange
        Long userId = 1L;

        // Mock the repository call
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.getUser(userId));
    }

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

        Trail trail = new Trail();
        trail.setAppId(1L);
        trail.setTrailName("Trail Name");
        trail.setTrailLength(5.0);
        trail.setTrailExperience(Experience.BEGINNER);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(trailRepository.save(any(Trail.class))).thenReturn(trail);

        // Act
        userService.saveTrailToUser(trailDTO, userId);

        // Assert
        verify(trailRepository).save(any(Trail.class));
        verify(userRepository).save(user);

        assertTrue(user.getFavoritedTrails().contains(trail));
    }

    @Test
    void testSaveTrailToUser_UserNotFound() {
        // Arrange
        Long userId = 1L;
        TrailDTO trailDTO = new TrailDTO();
        trailDTO.setAppId(1L);
        trailDTO.setName("Trail Name");
        trailDTO.setTrailLength(5.0);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () -> userService.saveTrailToUser(trailDTO, userId));
        assertEquals("Cannot find user with this ID", thrown.getMessage());
    }

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

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(trailRepository.findById(trailId)).thenReturn(Optional.of(trail));

        // Act
        userService.removeTrailFromUser(trailId, userId);

        // Assert
        verify(trailRepository).delete(trail);
        verify(userRepository).save(user);

        assertFalse(user.getFavoritedTrails().contains(trail));
    }

    @Test
    void testRemoveTrailFromUser_UserNotFound() {
        // Arrange
        Long userId = 1L;
        Long trailId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () -> userService.removeTrailFromUser(trailId, userId));
        assertEquals("Cannot find user with this ID: 1", thrown.getMessage());
    }

    @Test
    void testRemoveTrailFromUser_TrailNotFound() {
        // Arrange
        Long userId = 1L;
        Long trailId = 1L;

        User user = new User();
        user.setId(userId);
        Trail trail = new Trail();
        trail.setId(trailId);

        user.setFavoritedTrails(new ArrayList<>());
        user.getFavoritedTrails().add(trail);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(trailRepository.findById(trailId)).thenReturn(Optional.empty());

        // Act & Assert
        TrailNotFoundException thrown = assertThrows(TrailNotFoundException.class, () -> userService.removeTrailFromUser(trailId, userId));
        assertEquals("Cannot find trail with this ID: 1", thrown.getMessage());
    }

    @Test
    void testSaveTrailToUser_BeginnerExperience() throws UserNotFoundException {
        // Arrange
        Long userId = 1L;
        TrailDTO trailDTO = new TrailDTO();
        trailDTO.setAppId(1L);
        trailDTO.setName("Trail1");
        trailDTO.setTrailLength(5.0); // This should be Beginner experience

        User user = new User();
        user.setId(userId);
        user.setFavoritedTrails(new ArrayList<>());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(trailRepository.save(any(Trail.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        // Act
        userService.saveTrailToUser(trailDTO, userId);

        // Assert
        verify(trailRepository, times(1)).save(argThat(trail -> trail.getTrailExperience() == Experience.BEGINNER));
    }

    @Test
    void testSaveTrailToUser_IntermediateExperience() throws UserNotFoundException {
        // Arrange
        Long userId = 1L;
        TrailDTO trailDTO = new TrailDTO();
        trailDTO.setAppId(1L);
        trailDTO.setName("Trail2");
        trailDTO.setTrailLength(10.0); // This should be Intermediate experience

        User user = new User();
        user.setId(userId);
        user.setFavoritedTrails(new ArrayList<>());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(trailRepository.save(any(Trail.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        // Act
        userService.saveTrailToUser(trailDTO, userId);

        // Assert
        verify(trailRepository, times(1)).save(argThat(trail -> trail.getTrailExperience() == Experience.INTERMEDIATE));
    }

    @Test
    void testSaveTrailToUser_AdvancedExperience() throws UserNotFoundException {
        // Arrange
        Long userId = 1L;
        TrailDTO trailDTO = new TrailDTO();
        trailDTO.setAppId(1L);
        trailDTO.setName("Trail3");
        trailDTO.setTrailLength(15.0); // This should be Advanced experience

        User user = new User();
        user.setId(userId);
        user.setFavoritedTrails(new ArrayList<>());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(trailRepository.save(any(Trail.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        // Act
        userService.saveTrailToUser(trailDTO, userId);

        // Assert
        verify(trailRepository, times(1)).save(argThat(trail -> trail.getTrailExperience() == Experience.ADVANCED));
    }
}