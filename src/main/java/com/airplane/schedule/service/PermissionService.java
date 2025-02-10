package com.airplane.schedule.service;

import com.airplane.schedule.dto.request.PermissionRequest;
import com.airplane.schedule.dto.response.PermissionResponseDTO;
import com.airplane.schedule.exception.ResourceNotFoundException;
import com.airplane.schedule.mapper.PermissionMapper;
import com.airplane.schedule.model.Permission;
import com.airplane.schedule.model.Role;
import com.airplane.schedule.repository.PermissionRepository;
import com.airplane.schedule.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionService {
    private final PermissionRepository permissionsRepository;
    private final RoleRepository roleRepository;
    private final PermissionMapper permissionMapper;

    public PermissionResponseDTO createPermission(PermissionRequest permissionRequest) {
        Permission permission = permissionsRepository.findByResourceIdAndScope(permissionRequest.getResourceId(), permissionRequest.getScope());

        if(permission == null) {
            permission = Permission.builder()
                    .resourceId(permissionRequest.getResourceId())
                    .scope(permissionRequest.getScope())
                    .build();
            permissionsRepository.save(permission);
        }

        Role role = roleRepository.findById(permissionRequest.getRoleId()).orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        permission = permissionsRepository.save(permission);
        role.assignPermissionToRole(permission);
        roleRepository.save(role);
        return permissionMapper.PermissionTOPermissionResponseDTO(permission);
    }

    public List<PermissionResponseDTO> getAllPermission() {
        List<Permission> permissions = permissionsRepository.findAll();
        return permissions.stream().map(permissionMapper::PermissionTOPermissionResponseDTO).collect(Collectors.toList());
    }
}
