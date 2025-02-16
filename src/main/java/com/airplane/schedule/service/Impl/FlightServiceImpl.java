package com.airplane.schedule.service.Impl;

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
import com.airplane.schedule.service.FlightSevice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        int randomSixDigits = (int) (Math.random() * 900000) + 100000;
        flight.setFlightNumber("BNovel" + randomSixDigits);
        flightRepository.save(flight);
        return flightMapper.flightToFlightResponseDTO(flight);
    }

    @Override
    public List<FlightResponseDTO> getListFlightAvailableNotReturn(FlightAvailableRequestDTO flightAvailableRequestDTO) {
        int numberOfAdult = flightAvailableRequestDTO.getNumberOfAdult();
        int numberOfChildren = flightAvailableRequestDTO.getNumberOfChildren();
        List<Flight> departureFlights = flightRepository.findFlightsByDeparture(flightAvailableRequestDTO.getDepartureAirportCode(), flightAvailableRequestDTO.getArrivalAirportCode(), flightAvailableRequestDTO.getDepartureDay());
        List<FlightResponseDTO> flightResponseDTOs = new ArrayList<>();
        for (Flight flight : departureFlights) {
            FlightResponseDTO flightResponseDTO = flightMapper.flightToFlightResponseDTO(flight);
            flightResponseDTO.setTotalBusinessClassPrice(flight.getBusinessClassPrice() * numberOfAdult + (int)(0.7 * numberOfChildren * flight.getBusinessClassPrice()));
            flightResponseDTO.setTotalEconomyClassPrice(flight.getEconomyClassPrice() * numberOfAdult + (int)(0.7 * numberOfChildren * flight.getEconomyClassPrice()));
            flightResponseDTO.setTotalFirstClassPrice(flight.getFirstClassPrice() * numberOfAdult + (int)(0.7 * numberOfChildren * flight.getFirstClassPrice()));
            flightResponseDTOs.add(flightResponseDTO);
        }
        return flightResponseDTOs;
    }

    @Override
    public Map<String, List<FlightResponseDTO>> getListFlightAvailable(FlightAvailableRequestDTO flightAvailableRequestDTO) {
        int numberOfAdult = flightAvailableRequestDTO.getNumberOfAdult();
        int numberOfChildren = flightAvailableRequestDTO.getNumberOfChildren();
        List<Flight> departureFlights = flightRepository.findFlightsByDeparture(flightAvailableRequestDTO.getDepartureAirportCode(), flightAvailableRequestDTO.getArrivalAirportCode(), flightAvailableRequestDTO.getDepartureDay());
        List<FlightResponseDTO> departureFlightsResponseDTOs = new ArrayList<>();
        for (Flight flight : departureFlights) {
            FlightResponseDTO flightResponseDTO = flightMapper.flightToFlightResponseDTO(flight);
            flightResponseDTO.setTotalBusinessClassPrice(flight.getBusinessClassPrice() * numberOfAdult + (int)(0.7 * numberOfChildren * flight.getBusinessClassPrice()));
            flightResponseDTO.setTotalEconomyClassPrice(flight.getEconomyClassPrice() * numberOfAdult + (int)(0.7 * numberOfChildren * flight.getEconomyClassPrice()));
            flightResponseDTO.setTotalFirstClassPrice(flight.getFirstClassPrice() * numberOfAdult + (int)(0.7 * numberOfChildren * flight.getFirstClassPrice()));
            departureFlightsResponseDTOs.add(flightResponseDTO);
        }
        List<Flight> returnFlights = flightRepository.findFlightsByDeparture(flightAvailableRequestDTO.getArrivalAirportCode(), flightAvailableRequestDTO.getDepartureAirportCode(), flightAvailableRequestDTO.getReturnDay());
        List<FlightResponseDTO> returnFlightsResponseDTOs = new ArrayList<>();
        for (Flight flight : returnFlights) {
            FlightResponseDTO flightResponseDTO = flightMapper.flightToFlightResponseDTO(flight);
            flightResponseDTO.setTotalBusinessClassPrice(flight.getBusinessClassPrice() * numberOfAdult + (int)(0.7 * numberOfChildren * flight.getBusinessClassPrice()));
            flightResponseDTO.setTotalEconomyClassPrice(flight.getEconomyClassPrice() * numberOfAdult + (int)(0.7 * numberOfChildren * flight.getEconomyClassPrice()));
            flightResponseDTO.setTotalFirstClassPrice(flight.getFirstClassPrice() * numberOfAdult + (int)(0.7 * numberOfChildren * flight.getFirstClassPrice()));
            returnFlightsResponseDTOs.add(flightResponseDTO);
        }
        return Map.of("departureFlights", departureFlightsResponseDTOs,
                "returnFlights", returnFlightsResponseDTOs);
    }
}
