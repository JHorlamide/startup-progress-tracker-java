package com.olamide.startup_progress_tracker.mapper;

import com.olamide.startup_progress_tracker.DTO.PhaseDTO;
import com.olamide.startup_progress_tracker.entities.Phase;
import com.olamide.startup_progress_tracker.entities.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PhaseMapper {
    public static PhaseDTO mapToDTO(Phase phase) {
        PhaseDTO phaseDTO = new PhaseDTO();

        phaseDTO.setId(phase.getId());
        phaseDTO.setName(phase.getName());
        phaseDTO.setDone(phase.getDone());
        phaseDTO.setStage(phase.getStage());
        phaseDTO.setDescription(phase.getDescription());
        phaseDTO.setTasks(mapTasksToDTOs(phase.getTasks()));

        return phaseDTO;
    }
//
//    public static Phase mapToEntity(PhaseDTO phaseDTO) {
//        Phase phase = new Phase();
//
//        phase.setId(phaseDTO.getId());
//        phase.setName(phaseDTO.getName());
//        phase.setDone(phaseDTO.getDone());
//        phase.setStage(phaseDTO.getStage());
//        phase.setDescription(phaseDTO.getDescription());
//        phase.setTasks(mapDTOToTasks(phaseDTO.getTasks()));
//
//        return phase;
//    }

    public static List<PhaseDTO> mapToDTOs(List<Phase> phases) {
        return phases.stream()
                .map(PhaseMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    private static List<Map<String, Object>> mapTasksToDTOs(List<Task> tasks) {
        if (tasks == null) {
            return new ArrayList<>();
        }

        return tasks.stream()
                .map(task -> {
                    Map<String, Object> taskMap = new HashMap<>();

                    taskMap.put("id", task.getId());
                    taskMap.put("name", task.getName());
                    taskMap.put("completed", task.getCompleted());
                    taskMap.put("description", task.getDescription());

                    return taskMap;
                })
                .collect(Collectors.toList());
    }

    private static List<Task> mapDTOToTasks(List<Map<String, Object>> tasksMaps) {
        return tasksMaps.stream()
                .map(taskMap -> {
                    var task = new Task();

                    task.setId((Long) taskMap.get("id"));
                    task.setName((String) taskMap.get("name"));
                    task.setDescription((String) taskMap.get("description"));
                    task.setCompleted((Boolean) taskMap.get("completed"));

                    return task;
                })
                .collect(Collectors.toList());
    }
}
