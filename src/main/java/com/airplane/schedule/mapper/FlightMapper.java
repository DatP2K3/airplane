package com.airplane.schedule.mapper;

import com.airplane.schedule.dto.request.FlightRequestDTO;
import com.airplane.schedule.dto.response.FlightResponseDTO;
import com.airplane.schedule.dto.response.PermissionResponseDTO;
import com.airplane.schedule.model.Flight;
import com.airplane.schedule.model.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FlightMapper {
    Flight flightRequestDTOToFlight(FlightRequestDTO flightRequestDTO);
    @Mapping(source = "departureAirport.name", target = "departureAirportName")
    @Mapping(source = "arrivalAirport.name", target = "arrivalAirportName")
    @Mapping(source = "plane.model", target = "planeModel")
    FlightResponseDTO flightToFlightResponseDTO(Flight flight);
}
