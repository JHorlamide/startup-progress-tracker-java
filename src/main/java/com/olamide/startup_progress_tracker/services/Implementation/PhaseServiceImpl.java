package com.olamide.startup_progress_tracker.services.Implementation;

import com.olamide.startup_progress_tracker.DTO.PhaseDTO;
import com.olamide.startup_progress_tracker.DTO.TaskDTO;
import com.olamide.startup_progress_tracker.entities.Phase;
import com.olamide.startup_progress_tracker.entities.Task;
import com.olamide.startup_progress_tracker.exceptions.EntityNotFoundException;
import com.olamide.startup_progress_tracker.exceptions.PhaseNotCompletedException;
import com.olamide.startup_progress_tracker.exceptions.TaskAllReadyCompletedException;
import com.olamide.startup_progress_tracker.mapper.PhaseMapper;
import com.olamide.startup_progress_tracker.mapper.TaskMapper;
import com.olamide.startup_progress_tracker.repositories.PhaseRepository;
import com.olamide.startup_progress_tracker.repositories.TaskRepository;
import com.olamide.startup_progress_tracker.services.PhaseService;
import com.olamide.startup_progress_tracker.utils.PhaseSequenceGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PhaseServiceImpl implements PhaseService {
    private PhaseRepository phaseRepository;
    private TaskRepository taskRepository;
    private PhaseSequenceGenerator phaseSequenceGenerator;

    @Override
    public PhaseDTO createPhase(Phase phase) {
        Integer nextStage = phaseSequenceGenerator.getNextStage();

        phase.setDone(false);
        phase.setStage(nextStage);
        Phase createdPhase = phaseRepository.save(phase);
        return PhaseMapper.mapToDTO(createdPhase);
    }

    @Override
    public List<PhaseDTO> getAllPhases() {
        List<Phase> phases = (List<Phase>) phaseRepository.findAll();
        return PhaseMapper.mapToDTOs(phases);
    }

    @Override
    public TaskDTO createTask(Task task, Long phaseId) {
        Phase phase = phaseRepository.findById(phaseId)
                .orElseThrow(() -> new EntityNotFoundException(Phase.class, phaseId));

        task.setPhase(phase);
        task.setCompleted(false);
        Task createdTask = taskRepository.save(task);
        return TaskMapper.mapToDTO(createdTask);
    }

    @Override
    public PhaseDTO completeTask(Long phaseId, Long taskId) {
        // check if phase exist with the given phaseId
        Phase phase = phaseRepository.findById(phaseId)
                .orElseThrow(() -> new EntityNotFoundException(Phase.class, phaseId));

        // check task exist with the given taskId
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException(Task.class, taskId));

        // Check if the previous phase is completed
        if (!isPreviousPhaseCompleted(phase)) {
            throw new PhaseNotCompletedException();
        }

        // Mark task as completed
        updateTaskStatus(task, true);

        // Check if every task of the phase is completed
        if (areAllTasksCompleted(phase)) {
            phase.setDone(true);
            phaseRepository.save(phase);

            Phase nextPhase = getNextPhase(phase);
            if (nextPhase != null) {
                nextPhase.setDone(false);
                phaseRepository.save(nextPhase);
            }
        }

        return PhaseMapper.mapToDTO(phase);
    }

    @Override
    public PhaseDTO undoTask(Long phaseId, Long taskId) {
        // check if phase exist with the given phaseId
        Phase phase = phaseRepository.findById(phaseId)
                .orElseThrow(() -> new EntityNotFoundException(Phase.class, phaseId));

        // check task exist with the given taskId
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException(Task.class, taskId));

        // check if task is already completed
        if (!task.getCompleted()) {
            throw new TaskAllReadyCompletedException(taskId);
        }

        //update task status
        updateTaskStatus(task, false);

        boolean allTaskComplete = true;
        for (Task phaseTask : phase.getTasks()) {
            if (!phaseTask.getCompleted()) {
                allTaskComplete = false;
                break;
            }
        }

        phase.setDone(allTaskComplete);
        phaseRepository.save(phase);
        return PhaseMapper.mapToDTO(phase);
    }

    private Phase getPreviousPhase(Phase currentPhase) {
        Integer stage = currentPhase.getStage();
        return phaseRepository.findByStage(stage - 1);
    }

    private Phase getNextPhase(Phase currentPhase) {
        Integer stage = currentPhase.getStage();
        return phaseRepository.findByStage(stage + 1);
    }

    private boolean isPreviousPhaseCompleted(Phase phase) {
        Phase previousPhase = getPreviousPhase(phase);
        return previousPhase == null || previousPhase.getDone();
    }

    private void updateTaskStatus(Task task, boolean status) {
        task.setCompleted(status);
        taskRepository.save(task);
    }

    private boolean areAllTasksCompleted(Phase phase) {
        List<Task> tasks = phase.getTasks();
        return tasks.stream().allMatch(Task::getCompleted);
    }
}

