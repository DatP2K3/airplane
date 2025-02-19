package com.airplane.schedule.service.Impl;

import com.airplane.schedule.dto.PageApiResponse;
import com.airplane.schedule.dto.request.FlightAvailableRequestDTO;
import com.airplane.schedule.dto.request.FlightRequestDTO;
import com.airplane.schedule.dto.request.FlightSearchRequest;
import com.airplane.schedule.dto.response.FlightResponseDTO;
import com.airplane.schedule.dto.response.TicketResponseDTO;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightSevice {
    private final FlightRepository flightRepository;
    private final PlaneRepository planeRepository;
    private final AirportRepository airportRepository;
    private final FlightMapper flightMapper;

    @Override
    public FlightResponseDTO createFlight(FlightRequestDTO flightRequestDTO) {
        if(flightRequestDTO.getDepartureTime().after(flightRequestDTO.getArrivalTime())) {
            throw new IllegalArgumentException("Departure time must be before arrival time.");
        }
        Plane plane = planeRepository.findById(flightRequestDTO.getPlaneId()).orElseThrow(() -> new ResourceNotFoundException("Plane with id " + flightRequestDTO.getPlaneId() + " not found"));
        List<Plane> planes = planeRepository.findAvailablePlanes(flightRequestDTO.getDepartureTime(), flightRequestDTO.getArrivalTime());
        if(!planes.contains(plane)) {
            throw new IllegalArgumentException("Plane with id " + flightRequestDTO.getPlaneId() + " is not available at this time.");
        }
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
    public PageApiResponse<List<FlightResponseDTO>> searchFlight(FlightSearchRequest flightSearchRequest) {
        Long totalFlight= flightRepository.count(flightSearchRequest);
        List<Flight> flights = flightRepository.search(flightSearchRequest);
        List<FlightResponseDTO> flightResponseDTOS = flights.stream().map(flightMapper::flightToFlightResponseDTO).collect(Collectors.toList());
        PageApiResponse.PageableResponse pageableResponse = PageApiResponse.PageableResponse.builder()
                .pageSize(flightSearchRequest.getPageSize())
                .pageIndex(flightSearchRequest.getPageIndex())
                .totalElements(totalFlight)
                .totalPages((int)(Math.ceil((double)totalFlight / flightSearchRequest.getPageSize())))
                .hasNext((flightSearchRequest.getPageIndex() + 1) * flightSearchRequest.getPageSize() < totalFlight)
                .hasPrevious(flightSearchRequest.getPageIndex() >0).build();
        return PageApiResponse.<List<FlightResponseDTO>>builder()
                .data(flightResponseDTOS)
                .success(true)
                .code(200)
                .pageable(pageableResponse)
                .message("Search flights successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
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
