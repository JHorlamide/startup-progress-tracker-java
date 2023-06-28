package com.olamide.startup_progress_tracker.mapper;

import com.olamide.startup_progress_tracker.DTO.TaskDTO;
import com.olamide.startup_progress_tracker.entities.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskMapper {
    public static TaskDTO mapToDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();

        taskDTO.setId(task.getId());
        taskDTO.setName(task.getName());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setCompleted(task.getCompleted());
        taskDTO.setPhase(task.getPhase());

        return taskDTO;
    }

    public static Task mapToEntity(TaskDTO taskDTO) {
        Task task = new Task();

        task.setId(taskDTO.getId());
        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());
        task.setCompleted(taskDTO.getCompleted());
        task.setPhase(taskDTO.getPhase());

        return task;
    }


    public static List<TaskDTO> taskDTOS(List<Task> tasks) {
        List<TaskDTO> taskDTOS = new ArrayList<>();

        for(Task task: tasks) {
            TaskDTO taskDTO = mapToDTO(task);
            taskDTOS.add(taskDTO);
        }

        return taskDTOS;
    }
}
