package com.airplane.schedule.controller;

import com.airplane.schedule.dto.ApiResponse;
import com.airplane.schedule.dto.request.FlightAvailableRequestDTO;
import com.airplane.schedule.dto.request.FlightRequestDTO;
import com.airplane.schedule.dto.response.FlightResponseDTO;
import com.airplane.schedule.service.FlightServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {
    private final FlightServiceImpl flightService;
    @PostMapping("")
    ApiResponse<FlightResponseDTO> createFlight(@RequestBody FlightRequestDTO flightRequestDTO) {
        FlightResponseDTO flightResponseDTO = flightService.createFlight(flightRequestDTO);
        return ApiResponse.<FlightResponseDTO>builder()
                .data(flightResponseDTO)
                .success(true)
                .code(201)
                .message("Flight created successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PostMapping("/available-not-return")
    ApiResponse<List<FlightResponseDTO>> getListFlightAvailableNotReturn(@RequestBody FlightAvailableRequestDTO flightAvailableRequestDTO) {
        List<FlightResponseDTO> flightResponseDTOs = flightService.getListFlightAvailableNotReturn(flightAvailableRequestDTO);
        return ApiResponse.<List<FlightResponseDTO>>builder()
                .data(flightResponseDTOs)
                .success(true)
                .code(201)
                .message("Get list flight available successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PostMapping("/available")
    ApiResponse<Map<String, List<FlightResponseDTO>>> getListFlightAvailable(@RequestBody FlightAvailableRequestDTO flightAvailableRequestDTO) {
        Map<String, List<FlightResponseDTO>> flightResponseDTOs = flightService.getListFlightAvailable(flightAvailableRequestDTO);
        return ApiResponse.<Map<String, List<FlightResponseDTO>>>builder()
                .data(flightResponseDTOs)
                .success(true)
                .code(201)
                .message("Get list flight available successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }
}
