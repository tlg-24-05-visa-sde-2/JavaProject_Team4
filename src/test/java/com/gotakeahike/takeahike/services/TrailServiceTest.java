package com.gotakeahike.takeahike.services;

import com.gotakeahike.takeahike.models.Trail;
import com.gotakeahike.takeahike.repositories.TrailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrailServiceTest {

    @Mock
    private TrailRepository trailRepository;

    @InjectMocks
    private TrailService trailService;

    @BeforeEach
    void setUp() {
        // Initialize mocks and inject them
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllTrails_NoTrails() throws Exception {
        // Arrange
        when(trailRepository.findAll()).thenReturn(new ArrayList<>());

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> {
            trailService.findAllTrails();
        });
        assertEquals("No trails found", exception.getMessage());
    }

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