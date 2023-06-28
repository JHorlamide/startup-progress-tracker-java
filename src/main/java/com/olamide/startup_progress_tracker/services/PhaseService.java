package com.olamide.startup_progress_tracker.services;

import com.olamide.startup_progress_tracker.DTO.PhaseDTO;
import com.olamide.startup_progress_tracker.DTO.TaskDTO;
import com.olamide.startup_progress_tracker.entities.Phase;
import com.olamide.startup_progress_tracker.entities.Task;

import java.util.List;

public interface PhaseService {
    PhaseDTO createPhase(Phase phase);

    List<PhaseDTO> getAllPhases();

    TaskDTO createTask(Task task, Long phaseId);

    PhaseDTO completeTask(Long phaseId, Long taskId);

    PhaseDTO undoTask(Long phaseId, Long taskId);
}
