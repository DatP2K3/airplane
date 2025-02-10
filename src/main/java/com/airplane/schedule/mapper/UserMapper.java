package com.airplane.schedule.mapper;

import com.airplane.schedule.dto.request.UserRequestDTO;
import com.airplane.schedule.dto.response.UserResponseDTO;
import com.airplane.schedule.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "role.id", target = "roleId")
    UserResponseDTO userToUserResponseDTO(User user);
    User UserRequestDTOToUser(UserRequestDTO userRequestDTO);
}
