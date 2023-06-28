package com.olamide.startup_progress_tracker.exceptions;

public class PhaseNotFoundException extends RuntimeException {
    public PhaseNotFoundException(Long phaseId) {
        super("The phase with the given Id '" + phaseId + "' does not exist in our record");
    }
}
