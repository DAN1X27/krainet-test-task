package com.example.auth_service.services.impl;

import com.example.auth_service.dto.AdminShowUserDTO;
import com.example.auth_service.dto.UpdateUserDTO;
import com.example.auth_service.kafka.producers.NotificationProducer;
import com.example.auth_service.mapper.UserMapper;
import com.example.auth_service.models.User;
import com.example.auth_service.repositories.UsersRepository;
import com.example.auth_service.services.AdminUserService;
import com.example.auth_service.util.UserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
class AdminUserServiceImpl implements AdminUserService {

    private final UsersRepository usersRepository;

    private final UserMapper mapper;

    private final UserServiceImpl userService;

    private final NotificationProducer notificationProducer;

    @Override
    public List<AdminShowUserDTO> findAll(int page, int size) {
        List<User> users = usersRepository.findAll(
                PageRequest.of(page, size, Sort.Direction.DESC, "id")).getContent();
        return mapper.toListAdminShowDTO(users);
    }

    @Override
    public AdminShowUserDTO findByUsername(String username) {
        User user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new UserException("User not found"));
        return mapper.toAdminShowDTO(user);
    }

    @Override
    public AdminShowUserDTO findByEmail(String email) {
        User user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("User not found"));
        return mapper.toAdminShowDTO(user);
    }

    @Override
    @Transactional
    public void update(long id, UpdateUserDTO updateUserDTO) {
        User user = findById(id);
        boolean updated = userService.update(user, updateUserDTO);
        if (updated && user.getRole() == User.Role.ROLE_USER) {
            notificationProducer.sendUpdatedUserNotification(user);
        }
        log.info("Updated user by admin with username - {}", user.getUsername());
    }

    @Override
    @Transactional
    public void delete(long id) {
        User user = findById(id);
        usersRepository.delete(user);
        if (user.getRole() == User.Role.ROLE_USER) {
            notificationProducer.sendDeletedUserNotification(user);
        }
        log.info("Deleted user by admin with username - {}", user.getUsername());
    }

    private User findById(long id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new UserException("User not found"));
    }

}
