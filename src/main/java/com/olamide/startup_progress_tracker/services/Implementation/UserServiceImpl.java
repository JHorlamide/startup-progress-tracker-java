package com.olamide.startup_progress_tracker.services.Implementation;

import com.olamide.startup_progress_tracker.DTO.UserDTO;
import com.olamide.startup_progress_tracker.entities.User;
import com.olamide.startup_progress_tracker.exceptions.EntityNotFoundException;
import com.olamide.startup_progress_tracker.mapper.UserMapper;
import com.olamide.startup_progress_tracker.repositories.UserRepository;
import com.olamide.startup_progress_tracker.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDTO createUser(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        var createdUser = userRepository.save(user);
        return UserMapper.mapToDTO(createdUser);
    }

    @Override
    public UserDTO getUserByEmail(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException(User.class, userEmail));

        return UserMapper.mapToDTO(user);
    }

    @Override
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(User.class, userId));

        return UserMapper.mapToDTO(user);
    }
}
