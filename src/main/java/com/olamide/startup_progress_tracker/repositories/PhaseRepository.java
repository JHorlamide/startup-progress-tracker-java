package com.olamide.startup_progress_tracker.repositories;

import com.olamide.startup_progress_tracker.entities.Phase;
import org.springframework.data.repository.CrudRepository;

public interface PhaseRepository extends CrudRepository<Phase, Long> {
    Phase findByStage(Integer stage);
}
