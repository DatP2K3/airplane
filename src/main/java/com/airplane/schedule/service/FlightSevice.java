package com.airplane.schedule.service;

import com.airplane.schedule.dto.request.FlightAvailableRequestDTO;
import com.airplane.schedule.dto.request.FlightRequestDTO;
import com.airplane.schedule.dto.response.FlightResponseDTO;

import java.util.List;
import java.util.Map;

public interface FlightSevice {
    FlightResponseDTO createFlight(FlightRequestDTO flightRequestDTO);

    List<FlightResponseDTO> getListFlightAvailableNotReturn(FlightAvailableRequestDTO flightAvailableRequestDTO);

    Map<String, List<FlightResponseDTO>> getListFlightAvailable(FlightAvailableRequestDTO request);
}
