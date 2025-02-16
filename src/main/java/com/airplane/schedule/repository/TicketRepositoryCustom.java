package com.airplane.schedule.repository;

import com.airplane.schedule.dto.request.TicketSearchRequest;
import com.airplane.schedule.dto.request.UserSearchRequest;
import com.airplane.schedule.model.Ticket;
import com.airplane.schedule.model.User;

import java.util.List;

public interface TicketRepositoryCustom {
    List<Ticket> search(TicketSearchRequest ticketSearchRequest);
    Long count(TicketSearchRequest ticketSearchRequest);
}
