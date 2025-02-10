package com.airplane.schedule.mapper;

import com.airplane.schedule.dto.request.AirportRequestDTO;
import com.airplane.schedule.dto.request.UserRequestDTO;
import com.airplane.schedule.dto.response.AirportResponseDTO;
import com.airplane.schedule.dto.response.UserResponseDTO;
import com.airplane.schedule.model.Airport;
import com.airplane.schedule.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AirportMapper {
    AirportResponseDTO airportToAirportResponseDTO(Airport airport);
    Airport airportRequestDTOToAirport(AirportRequestDTO airportRequestDTO);
}
