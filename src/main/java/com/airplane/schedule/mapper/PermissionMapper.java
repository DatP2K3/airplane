package com.airplane.schedule.mapper;

import com.airplane.schedule.dto.request.UserRequestDTO;
import com.airplane.schedule.dto.response.PermissionResponseDTO;
import com.airplane.schedule.dto.response.UserResponseDTO;
import com.airplane.schedule.model.Permission;
import com.airplane.schedule.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    @Mapping(source = "role.id", target = "roleId")
    PermissionResponseDTO PermissionTOPermissionResponseDTO(Permission permission);
}
