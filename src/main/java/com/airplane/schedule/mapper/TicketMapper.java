package com.airplane.schedule.mapper;

import com.airplane.schedule.dto.request.TicketRequestDTO;
import com.airplane.schedule.dto.response.TicketResponseDTO;
import com.airplane.schedule.model.Ticket;
import com.airplane.schedule.model.Seat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = {Seat.class, Collectors.class, ArrayList.class})
public interface TicketMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "fullName", expression = "java(ticket.getFirstName() + \" \" + ticket.getLastName())")
    @Mapping(target = "flightId", source = "flight.id")
    @Mapping(target = "seatNumbers", expression = "java(ticket.getSeats() != null ? ticket.getSeats().stream().map(Seat::getSeatNumber).collect(Collectors.toList()) : new ArrayList<>())")
    TicketResponseDTO ticketToTicketResponseDTO(Ticket ticket);

    Ticket ticketRequestDTOToTicket(TicketRequestDTO ticketRequestDTO);
}