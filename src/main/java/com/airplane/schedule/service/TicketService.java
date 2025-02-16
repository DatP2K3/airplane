package com.airplane.schedule.service;

import com.airplane.schedule.dto.PageApiResponse;
import com.airplane.schedule.dto.request.TicketRequestDTO;
import com.airplane.schedule.dto.request.TicketSearchRequest;
import com.airplane.schedule.dto.response.TicketResponseDTO;
import com.airplane.schedule.model.Ticket;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface TicketService {
    TicketResponseDTO createTicket (TicketRequestDTO ticketRequestDTO, HttpServletRequest request);
    String updateTicketStatus(String ticketNumber, String status);
    PageApiResponse<List<TicketResponseDTO>> searchTicket(TicketSearchRequest ticketSearchRequest);
    TicketResponseDTO getTicketByTicketNumber(String ticketNumber);
}
