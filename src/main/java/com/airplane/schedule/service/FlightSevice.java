package com.airplane.schedule.service;

import com.airplane.schedule.dto.PageApiResponse;
import com.airplane.schedule.dto.request.FlightAvailableRequestDTO;
import com.airplane.schedule.dto.request.FlightRequestDTO;
import com.airplane.schedule.dto.request.FlightSearchRequest;
import com.airplane.schedule.dto.request.TicketSearchRequest;
import com.airplane.schedule.dto.response.FlightResponseDTO;
import com.airplane.schedule.dto.response.TicketResponseDTO;

import java.util.List;
import java.util.Map;

public interface FlightSevice {
    FlightResponseDTO createFlight(FlightRequestDTO flightRequestDTO);

    List<FlightResponseDTO> getListFlightAvailableNotReturn(FlightAvailableRequestDTO flightAvailableRequestDTO);
    PageApiResponse<List<FlightResponseDTO>> searchFlight(FlightSearchRequest flightSearchRequest);
    Map<String, List<FlightResponseDTO>> getListFlightAvailable(FlightAvailableRequestDTO request);
}
