package com.olamide.startup_progress_tracker.exceptions;

public class PhaseNotCompletedException extends RuntimeException {
    public PhaseNotCompletedException() {
        super("Cannot mark phase as completed unless all phase in the previous phase is completed");
    }
}
