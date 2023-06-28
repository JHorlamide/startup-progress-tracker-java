package com.olamide.startup_progress_tracker.mapper;

import com.olamide.startup_progress_tracker.DTO.UserDTO;
import com.olamide.startup_progress_tracker.entities.User;

public class UserMapper {
    public static UserDTO mapToDTO(User user) {
        var userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setFullName(user.getFullName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());

        return userDTO;
    }
}
