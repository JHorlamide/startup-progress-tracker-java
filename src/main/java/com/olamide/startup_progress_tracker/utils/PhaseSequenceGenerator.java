package com.olamide.startup_progress_tracker.utils;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class PhaseSequenceGenerator {
    private final AtomicLong sequence = new AtomicLong(0);

    public Integer getNextStage() {
        return (int)sequence.incrementAndGet();
    }
}
