package com.example.auth_service.services;

import com.example.auth_service.dto.CreateUserDTO;
import com.example.auth_service.dto.ShowUserDTO;
import com.example.auth_service.dto.UpdateUserDTO;
import com.example.auth_service.kafka.producers.NotificationProducer;
import com.example.auth_service.mapper.UserMapper;
import com.example.auth_service.models.User;
import com.example.auth_service.repositories.UsersRepository;
import com.example.auth_service.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper mapper;

    private final NotificationProducer notificationProducer;

    public ShowUserDTO show() {
        return mapper.toShowDTO(getCurrentUser());
    }

    @Transactional
    public void create(CreateUserDTO createUserDTO) {
        User user = mapper.fromCreateDTO(createUserDTO);
        user.setCreatedAt(LocalDate.now());
        user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
        user.setRole(User.Role.ROLE_USER);
        usersRepository.save(user);
        notificationProducer.sendCreatedUserNotification(createUserDTO);
        log.info("Created new user with username - {}", createUserDTO.getUsername());
    }

    @Transactional
    public void update(UpdateUserDTO updateUserDTO) {
        User user = usersRepository.findById(getCurrentUser().getId())
                .orElseThrow(() -> new IllegalStateException("User not found"));
        boolean updated = update(user, updateUserDTO);
        if (updated && user.getRole() == User.Role.ROLE_USER) {
            notificationProducer.sendUpdatedUserNotification(user);
        }
        log.info("Updated user with username - {}", user.getUsername());
    }

    @Transactional
    public void delete() {
        User user = getCurrentUser();
        usersRepository.delete(user);
        if (user.getRole() == User.Role.ROLE_USER) {
            notificationProducer.sendDeletedUserNotification(user);
        }
        log.info("Deleted user with username - {}", user.getUsername());
    }

    boolean update(User user, UpdateUserDTO updateUserDTO) {
        boolean updated = false;
        if (updateUserDTO.getUsername() != null) {
            updated = true;
            user.setUsername(updateUserDTO.getUsername());
        }
        if (updateUserDTO.getEmail() != null) {
            updated = true;
            user.setEmail(updateUserDTO.getEmail());
        }
        if (updateUserDTO.getFirstName() != null) {
            updated = true;
            user.setFirstName(updateUserDTO.getFirstName());
        }
        if (updateUserDTO.getLastName() != null) {
            updated = true;
            user.setLastName(updateUserDTO.getLastName());
        }
        if (updateUserDTO.getPassword() != null) {
            updated = true;
            user.setPassword(passwordEncoder.encode(updateUserDTO.getPassword()));
        }
        return updated;
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails.user();
    }

}
