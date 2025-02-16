package com.airplane.schedule.controller;

import com.airplane.schedule.dto.ApiResponse;
import com.airplane.schedule.dto.request.AirportRequestDTO;
import com.airplane.schedule.dto.response.AirportResponseDTO;
import com.airplane.schedule.service.Impl.AirportServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/airports")
@RequiredArgsConstructor
public class AirportController {
    private final AirportServiceImpl airportService;

    @PreAuthorize("hasPermission('airport', 'create')")
    @PostMapping("")
    ApiResponse<AirportResponseDTO> createAirport(@RequestBody AirportRequestDTO airportRequestDTO) {
        AirportResponseDTO airportResponseDTO = airportService.createAirport(airportRequestDTO);
        return ApiResponse.<AirportResponseDTO>builder()
                .data(airportResponseDTO)
                .success(true)
                .code(201)
                .message("Airport created successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @GetMapping("/{code}")
    ApiResponse<AirportResponseDTO> getAirportByCode(@PathVariable String code) {
        AirportResponseDTO airportResponseDTO = airportService.getAirportByCode(code);
        return ApiResponse.<AirportResponseDTO>builder()
                .data(airportResponseDTO)
                .success(true)
                .code(200)
                .message("Airport retrieved successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PreAuthorize("hasPermission('airport', 'update')")
    @PatchMapping("/{id}")
    ApiResponse<AirportResponseDTO> updateAirport(@PathVariable int id, @RequestBody AirportRequestDTO airportRequestDTO) {
        AirportResponseDTO airportResponseDTO = airportService.updateAirport(id, airportRequestDTO);
        return ApiResponse.<AirportResponseDTO>builder()
                .data(airportResponseDTO)
                .success(true)
                .code(200)
                .message("Airport updated successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }
}
