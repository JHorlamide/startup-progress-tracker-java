package com.olamide.startup_progress_tracker.repositories;

import com.olamide.startup_progress_tracker.entities.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {

}
