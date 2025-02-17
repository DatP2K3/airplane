package com.airplane.schedule.service.Impl;

import com.airplane.schedule.dto.response.StatisticResponseDTO;
import com.airplane.schedule.repository.FlightRepository;
import com.airplane.schedule.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticService {
    private final TicketRepository ticketRepository;
    private final FlightRepository flightRepository;
    public StatisticResponseDTO getStatistic() {
        Long totalRevenue = ticketRepository.getTotalRevenueToday();
        int totalTicket = ticketRepository.getTotalTicketsToday();
        int totalFlight = flightRepository.getTotalFlightsToday();
        return StatisticResponseDTO.builder()
                .totalRevenue(totalRevenue)
                .toltalTicket(totalTicket)
                .totalFlight(totalFlight)
                .build();
    }
}
