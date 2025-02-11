package com.airplane.schedule.service;

import com.airplane.schedule.dto.request.AirportRequestDTO;
import com.airplane.schedule.dto.response.AirportResponseDTO;
import com.airplane.schedule.exception.ResourceNotFoundException;
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

    @Override
    public AirportResponseDTO updateAirport(int id, AirportRequestDTO airportRequestDTO) {
        Airport airport = airportRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Airport with id " + id + " not found"));
        if(airportRequestDTO.getName() != null) {
            airport.setName(airportRequestDTO.getName());
        }
        if(airportRequestDTO.getCity() != null) {
            airport.setCity(airportRequestDTO.getCity());
        }
        if(airportRequestDTO.getCountry() != null) {
            airport.setCountry(airportRequestDTO.getCountry());
        }
        if(airportRequestDTO.getCode() != null) {
            airport.setCode(airportRequestDTO.getCode());
        }
        return airportMapper.airportToAirportResponseDTO(airportRepository.save(airport));
    }
}
