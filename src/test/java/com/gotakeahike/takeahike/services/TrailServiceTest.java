package com.gotakeahike.takeahike.services;

import com.gotakeahike.takeahike.Exceptions.TrailNotFoundException;import com.gotakeahike.takeahike.models.Trail;
import com.gotakeahike.takeahike.repositories.TrailRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link TrailService}.
 * <p>
 * This class uses Mockito to mock dependencies and test the behavior of the {@link TrailService} class.
 * It includes tests for different scenarios of the {@code findAllTrails} method.
 */
@ExtendWith(MockitoExtension.class)
public class TrailServiceTest {

    @Mock
    private TrailRepository trailRepository;

    @InjectMocks
    private TrailService trailService;

    /**
     * Tests the {@link TrailService#findAllTrails()} method when no trails are available.
     * <p>
     * This test ensures that a {@link TrailNotFoundException} is thrown with the expected message
     * when the {@link TrailRepository} returns an empty list.
     *
     * @throws TrailNotFoundException if the exception is thrown during the test
     */
    @Test
    void testFindAllTrails_NoTrails() throws TrailNotFoundException {
        // Arrange
        when(trailRepository.findAll()).thenReturn(new ArrayList<>());

        // Act & Assert
        Exception exception = assertThrows(TrailNotFoundException.class, () -> {
            trailService.findAllTrails();
        });
        assertEquals("No trails found", exception.getMessage());
    }

    /**
     * Tests the {@link TrailService#findAllTrails()} method when some trails are available.
     * <p>
     * This test ensures that the method returns the correct list of trails when the
     * {@link TrailRepository} returns a non-empty list.
     *
     * @throws Exception if an exception is thrown during the test
     */
    @Test
    void testFindAllTrails_SomeTrails() throws Exception {
        // Arrange
        List<Trail> expectedTrails = List.of(new Trail(), new Trail());
        when(trailRepository.findAll()).thenReturn(expectedTrails);

        // Act
        List<Trail> actualTrails = trailService.findAllTrails();

        // Assert
        assertNotNull(actualTrails);
        assertEquals(expectedTrails.size(), actualTrails.size());
        assertEquals(expectedTrails, actualTrails);
    }
}