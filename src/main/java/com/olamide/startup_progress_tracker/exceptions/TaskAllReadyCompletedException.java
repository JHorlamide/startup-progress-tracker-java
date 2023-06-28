package com.olamide.startup_progress_tracker.exceptions;

public class TaskAllReadyCompletedException extends RuntimeException {
    public TaskAllReadyCompletedException(Long taskId) {
        super("Task is not completed");
    }
}
