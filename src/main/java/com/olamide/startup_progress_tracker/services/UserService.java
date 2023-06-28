package com.olamide.startup_progress_tracker.services;

import com.olamide.startup_progress_tracker.DTO.UserDTO;
import com.olamide.startup_progress_tracker.entities.User;

public interface UserService {
    UserDTO createUser(User user);

    UserDTO getUserByEmail(String userEmail);

    UserDTO getUserById(Long userId);
}
