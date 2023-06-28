package com.olamide.startup_progress_tracker.controller;

import com.olamide.startup_progress_tracker.DTO.PhaseDTO;
import com.olamide.startup_progress_tracker.DTO.TaskDTO;
import com.olamide.startup_progress_tracker.entities.Phase;
import com.olamide.startup_progress_tracker.entities.Task;
import com.olamide.startup_progress_tracker.services.PhaseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/phases")
public class PhaseController {
    PhaseService phaseService;

    @GetMapping
    public ResponseEntity<List<PhaseDTO>> getPhases() {
        List<PhaseDTO> phases = phaseService.getAllPhases();
        return ResponseEntity.ok(phases);
    }

    @PostMapping
    public ResponseEntity<PhaseDTO> createPhase(@Valid @RequestBody Phase phase) {
        PhaseDTO createdPhaseDTO = phaseService.createPhase(phase);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPhaseDTO);
    }

    @PostMapping("/{phaseId}/tasks")
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody Task task, @PathVariable Long phaseId) {
        TaskDTO createdTaskDTO = phaseService.createTask(task, phaseId);
        return ResponseEntity.ok(createdTaskDTO);
    }

    @PutMapping("/{phaseId}/tasks/{taskId}")
    public ResponseEntity<PhaseDTO> updateTask(
            @PathVariable Long phaseId, @PathVariable Long taskId) {
        PhaseDTO updatedPhaseDTO = phaseService.completeTask(phaseId, taskId);
        return ResponseEntity.ok(updatedPhaseDTO);
    }

    @PutMapping("/{phaseId}/tasks/{taskId}/reopen")
    public ResponseEntity<PhaseDTO> reopenTask(
            @PathVariable Long phaseId, @PathVariable Long taskId) {
        PhaseDTO updatedPhaseDTO = phaseService.undoTask(phaseId, taskId);
        return ResponseEntity.ok(updatedPhaseDTO);
    }
}
