package com.airplane.schedule.config.security;

import com.airplane.schedule.model.Permission;
import com.airplane.schedule.model.Role;
import com.airplane.schedule.model.User;
import com.airplane.schedule.repository.PermissionRepository;
import com.airplane.schedule.repository.RoleRepository;
import com.airplane.schedule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomPermissionEvaluator implements PermissionEvaluator {
    private final UserRepository userRepository;
    private  final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (authentication == null || targetDomainObject == null || permission == null) {
            return false;
        }
        String email = authentication.getName();

        String resurceId = (String) targetDomainObject;
        String scope = (String) permission;

        return checkPermissionForUser(email, resurceId, scope);
    }

    private boolean checkPermissionForUser(String email, String resurceId, String scope) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        if (user == null) {
            return false;
        }
        Role role = user.getRole();
        if(role.isRoot()) {
            return true;
        }

        Permission permission = permissionRepository.findPermissionByRoleIdAndScopeAndResourceId(role.getId(), scope, resurceId);
        if(permission != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
