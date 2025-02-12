package com.airplane.schedule.controller;

import com.airplane.schedule.dto.ApiResponse;
import com.airplane.schedule.dto.request.PlaneAvailableRequestDTO;
import com.airplane.schedule.dto.request.PlaneRequestDTO;
import com.airplane.schedule.dto.response.PlaneResponseDTO;
import com.airplane.schedule.service.PlaneServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/planes")
@RequiredArgsConstructor
public class PlaneController {
    private final PlaneServiceImpl planeService;

    @PostMapping("")
    ApiResponse<PlaneResponseDTO> createPlane(@RequestBody PlaneRequestDTO planeRequestDTO) {
        PlaneResponseDTO planeResponseDTO = planeService.createPlane(planeRequestDTO);
        return ApiResponse.<PlaneResponseDTO>builder()
                .data(planeResponseDTO)
                .success(true)
                .code(201)
                .message("Plane created successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PostMapping("/available")
    ApiResponse<List<PlaneResponseDTO>> getListPlaneAvailable(@RequestBody PlaneAvailableRequestDTO planeAvailableRequestDTO) {
        List<PlaneResponseDTO> planeResponseDTOs = planeService.getListPlaneavailable(planeAvailableRequestDTO);
        return ApiResponse.<List<PlaneResponseDTO>>builder()
                .data(planeResponseDTOs)
                .success(true)
                .code(201)
                .message("Get list plane available successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }
}
