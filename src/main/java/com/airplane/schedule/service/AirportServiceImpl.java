package com.airplane.schedule.service;

import com.airplane.schedule.dto.request.AirportRequestDTO;
import com.airplane.schedule.dto.response.AirportResponseDTO;
import com.airplane.schedule.mapper.AirportMapper;
import com.airplane.schedule.model.Airport;
import com.airplane.schedule.repository.AirportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AirportServiceImpl implements AirportService {
    private final AirportRepository airportRepository;
    private final AirportMapper airportMapper;

    @Override
    public AirportResponseDTO createAirport(AirportRequestDTO airportRequestDTO) {
        Airport airport = airportMapper.airportRequestDTOToAirport(airportRequestDTO);
        return airportMapper.airportToAirportResponseDTO(airportRepository.save(airport));
    }

    @Override
    public AirportResponseDTO getAirportByCode(String code) {
        Airport airport = airportRepository.findByCode(code);
        return airportMapper.airportToAirportResponseDTO(airport);
    }

    @Override
    public List<AirportResponseDTO> getPageAirports() {
        return List.of();
    }


}
