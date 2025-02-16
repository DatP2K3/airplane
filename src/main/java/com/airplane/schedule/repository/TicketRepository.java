package com.airplane.schedule.repository;

import com.airplane.schedule.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Integer>, TicketRepositoryCustom {
    Optional<Ticket> findByTicketNumber(String txnRef);
}
