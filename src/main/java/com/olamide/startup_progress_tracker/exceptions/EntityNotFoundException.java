package com.olamide.startup_progress_tracker.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Class<?> entity, Long id) {
        super("The " + entity.getSimpleName().toLowerCase() + " with id '" + id + "' does not exist in our record");
    }

    public EntityNotFoundException(Class<?> entity, String identifier) {
        super("The " + entity.getSimpleName().toLowerCase() + " with identifier '" + identifier + "' does not exist in our record");
    }
}
