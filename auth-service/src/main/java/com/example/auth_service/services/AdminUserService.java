package com.example.auth_service.services;

import com.example.auth_service.dto.ShowUserDTO;
import com.example.auth_service.dto.UpdateUserDTO;
import com.example.auth_service.kafka.producers.NotificationProducer;
import com.example.auth_service.mapper.UserMapper;
import com.example.auth_service.models.User;
import com.example.auth_service.repositories.UsersRepository;
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
public class AdminUserService {

    private final UsersRepository usersRepository;

    private final UserMapper mapper;

    private final UserService userService;

    private final NotificationProducer notificationProducer;

    public List<ShowUserDTO> findAll(int page, int size) {
        List<User> users = usersRepository.findAll(
                PageRequest.of(page, size, Sort.Direction.DESC, "id")).getContent();
        return mapper.toListShowDTO(users);
    }

    public ShowUserDTO findByUsername(String username) {
        User user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new UserException("User not found"));
        return mapper.toShowDTO(user);
    }

    public ShowUserDTO findByEmail(String email) {
        User user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("User not found"));
        return mapper.toShowDTO(user);
    }

    @Transactional
    public void update(long id, UpdateUserDTO updateUserDTO) {
        User user = findById(id);
        boolean updated = userService.update(user, updateUserDTO);
        if (updated && user.getRole() == User.Role.ROLE_USER) {
            notificationProducer.sendUpdatedUserNotification(user);
        }
        log.info("Updated user by admin with username - {}", user.getUsername());
    }

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
