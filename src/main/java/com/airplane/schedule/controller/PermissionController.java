package com.airplane.schedule.controller;

import com.airplane.schedule.dto.ApiResponse;
import com.airplane.schedule.dto.request.PermissionRequest;
import com.airplane.schedule.dto.request.PermissionSearchRequest;
import com.airplane.schedule.dto.response.PageApiResponse;
import com.airplane.schedule.dto.response.PermissionResponseDTO;
import com.airplane.schedule.model.Permission;
import com.airplane.schedule.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;

//    @PreAuthorize("hasPermission(null, 'permission.create')")
    @PostMapping("/permissions")
    public ApiResponse<PermissionResponseDTO> createPermission(@RequestBody PermissionRequest permissionRequest) {
        PermissionResponseDTO permissionResponseDTO = permissionService.createPermission(permissionRequest);
        return ApiResponse.<PermissionResponseDTO>builder()
                .data(permissionResponseDTO)
                .success(true)
                .code(201)
                .message("Permission created successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @GetMapping("/permissions")
//    @PreAuthorize("hasPermission(null, 'permission.read')")
    public ApiResponse<List<PermissionResponseDTO>> getAllPermissions(@RequestBody PermissionSearchRequest permissionSearchRequest) {
        List<PermissionResponseDTO> permissionResponseDTOS = permissionService.getAllPermission();
        return ApiResponse.<List<PermissionResponseDTO>>builder()
                .data(permissionResponseDTOS)
                .success(true)
                .code(200)
                .message("Get permissions successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }
}
