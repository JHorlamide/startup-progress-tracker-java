package com.olamide.startup_progress_tracker.services;

import com.olamide.startup_progress_tracker.DTO.PhaseDTO;
import com.olamide.startup_progress_tracker.entities.Phase;
import com.olamide.startup_progress_tracker.repositories.PhaseRepository;
import com.olamide.startup_progress_tracker.repositories.TaskRepository;
import com.olamide.startup_progress_tracker.services.Implementation.PhaseServiceImpl;
import com.olamide.startup_progress_tracker.utils.PhaseSequenceGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PhaseServiceImplTest {
    private PhaseServiceImpl phaseService;

    @Mock
    private PhaseRepository phaseRepository;

    @Mock
    private PhaseSequenceGenerator phaseSequenceGenerator;

    @Mock
    private TaskRepository taskRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        phaseService = new PhaseServiceImpl(phaseRepository, taskRepository, phaseSequenceGenerator);
    }

    @Test
    void createPhase_ShouldReturnCreatedPhaseDTO(){
        // Arrange
        Phase phase = new Phase();
        phase.setDone(false);
        phase.setStage(1);
        when(phaseSequenceGenerator.getNextStage()).thenReturn(1);
        when(phaseRepository.save(phase)).thenReturn(phase);

        // Act
        PhaseDTO result = phaseService.createPhase(phase);

        // Assert
        assertNotNull(result);
        assertEquals(phase.getStage(), result.getStage());
        assertFalse(result.getDone());
        verify(phaseRepository, times(1)).save(phase);
    }
}
