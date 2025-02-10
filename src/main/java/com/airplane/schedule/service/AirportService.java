package com.airplane.schedule.service;

import com.airplane.schedule.dto.request.AirportRequestDTO;
import com.airplane.schedule.dto.response.AirportResponseDTO;

import java.util.List;

public interface AirportService {
    AirportResponseDTO createAirport(AirportRequestDTO airportRequestDTO);
    AirportResponseDTO getAirportByCode(String code);
    List<AirportResponseDTO> getPageAirports();
}
