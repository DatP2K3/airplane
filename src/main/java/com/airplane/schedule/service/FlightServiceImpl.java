package com.airplane.schedule.service;

import com.airplane.schedule.dto.request.FlightAvailableRequestDTO;
import com.airplane.schedule.dto.request.FlightRequestDTO;
import com.airplane.schedule.dto.response.FlightResponseDTO;
import com.airplane.schedule.exception.ResourceNotFoundException;
import com.airplane.schedule.mapper.FlightMapper;
import com.airplane.schedule.model.Airport;
import com.airplane.schedule.model.Flight;
import com.airplane.schedule.model.Plane;
import com.airplane.schedule.repository.AirportRepository;
import com.airplane.schedule.repository.FlightRepository;
import com.airplane.schedule.repository.PlaneRepository;
import com.airplane.schedule.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightSevice {
    private final FlightRepository flightRepository;
    private final PlaneRepository planeRepository;
    private final SeatRepository seatRepository;
    private final AirportRepository airportRepository;
    private final FlightMapper flightMapper;

    @Override
    public FlightResponseDTO createFlight(FlightRequestDTO flightRequestDTO) {
        if(flightRequestDTO.getDepartureTime().after(flightRequestDTO.getArrivalTime())) {
            throw new IllegalArgumentException("Departure time must be before arrival time.");
        }
        Plane plane = planeRepository.findById(flightRequestDTO.getPlaneId()).orElseThrow(() -> new ResourceNotFoundException("Plane with id " + flightRequestDTO.getPlaneId() + " not found"));
        Flight flight = flightMapper.flightRequestDTOToFlight(flightRequestDTO);
        flight.setPlane(plane);
        Airport deppature = airportRepository.findByCode(flightRequestDTO.getDepartureAirportCode());
        Airport arrival = airportRepository.findByCode(flightRequestDTO.getArrivalAirportCode());
        flight.setDepartureAirport(deppature);
        flight.setArrivalAirport(arrival);
        flight.setDepartureTime(flightRequestDTO.getDepartureTime());
        flight.setArrivalTime(flightRequestDTO.getArrivalTime());
        flight.setBusinessClassPrice(flightRequestDTO.getBusinessClassPrice());
        flight.setEconomyClassPrice(flightRequestDTO.getEconomyClassPrice());
        flight.setFirstClassPrice(flightRequestDTO.getFirstClassPrice());
        flight.setStatus(flightRequestDTO.getStatus());
        flightRepository.save(flight);
        return flightMapper.flightToFlightResponseDTO(flight);
    }

    @Override
    public List<FlightResponseDTO> getListFlightAvailableNotReturn(FlightAvailableRequestDTO flightAvailableRequestDTO) {
        List<Flight> departureFlights = flightRepository.findFlightsByDeparture(flightAvailableRequestDTO.getDepartureAirportCode(), flightAvailableRequestDTO.getArrivalAirportCode(), flightAvailableRequestDTO.getDepartureDay());
        return departureFlights.stream().map(flightMapper::flightToFlightResponseDTO).toList();
    }

    @Override
    public Map<String, List<FlightResponseDTO>> getListFlightAvailable(FlightAvailableRequestDTO request) {
        return Map.of();
    }
}
