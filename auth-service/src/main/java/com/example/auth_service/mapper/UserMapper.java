package com.example.auth_service.mapper;

import com.example.auth_service.dto.CreateUserDTO;
import com.example.auth_service.kafka.messages.CreatedUserMessage;
import com.example.auth_service.kafka.messages.UserMessage;
import com.example.auth_service.dto.ShowUserDTO;
import com.example.auth_service.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    User fromCreateDTO(CreateUserDTO createUserDTO);

    List<ShowUserDTO> toListShowDTO(List<User> users);

    ShowUserDTO toShowDTO(User user);

    UserMessage toUserNotificationDTO(User user);

    CreatedUserMessage toCreatedUserNotificationDTOFromCreateDTO(CreateUserDTO createUserDTO);
}
