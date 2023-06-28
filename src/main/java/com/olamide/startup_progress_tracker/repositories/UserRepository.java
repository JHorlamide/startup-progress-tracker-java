package com.olamide.startup_progress_tracker.repositories;

import com.olamide.startup_progress_tracker.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
