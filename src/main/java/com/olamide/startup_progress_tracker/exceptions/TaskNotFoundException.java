package com.olamide.startup_progress_tracker.exceptions;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long taskId) {
        super("The task with the given Id '" + taskId + "' does not exist");
    }
}
