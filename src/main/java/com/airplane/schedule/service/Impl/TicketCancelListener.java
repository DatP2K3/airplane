package com.airplane.schedule.service.Impl;

import com.airplane.schedule.config.RabbitMQConfig;
import com.airplane.schedule.exception.ResourceNotFoundException;
import com.airplane.schedule.model.Ticket;
import com.airplane.schedule.model.enums.TicketStatus;
import com.airplane.schedule.repository.TicketRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketCancelListener {
    private final TicketRepository ticketRepository;

    @Transactional
    @RabbitListener(queues = RabbitMQConfig.DEAD_LETTER_QUEUE)
    public void processCancelTicket(int ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new ResourceNotFoundException("Ticket with id " + ticketId + " not found"));
        if (ticket.getStatus().equals(TicketStatus.PENDING.getDisplayName())) {
            ticket.setStatus(TicketStatus.CANCELLED.getDisplayName());
            ticket.getSeats().forEach(seat -> seat.setBooked(false));
            ticketRepository.save(ticket);
        }
    }
}
