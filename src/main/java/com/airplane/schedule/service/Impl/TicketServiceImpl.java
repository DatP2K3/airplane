package com.airplane.schedule.service.Impl;

import com.airplane.schedule.config.RabbitMQConfig;
import com.airplane.schedule.config.VNPAYConfig;
import com.airplane.schedule.dto.PageApiResponse;
import com.airplane.schedule.dto.request.TicketRequestDTO;
import com.airplane.schedule.dto.request.TicketSearchRequest;
import com.airplane.schedule.dto.response.TicketResponseDTO;
import com.airplane.schedule.dto.response.UserResponseDTO;
import com.airplane.schedule.exception.AppException;
import com.airplane.schedule.exception.ErrorCode;
import com.airplane.schedule.mapper.TicketMapper;
import com.airplane.schedule.model.*;
import com.airplane.schedule.model.enums.TicketStatus;
import com.airplane.schedule.repository.FlightRepository;
import com.airplane.schedule.repository.TicketRepository;
import com.airplane.schedule.service.TicketService;
import com.airplane.schedule.util.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final RabbitTemplate rabbitTemplate;
    private final FlightRepository flightRepository;
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final VNPAYConfig vnPayConfig;

    @Override
    public TicketResponseDTO createTicket(TicketRequestDTO ticketRequestDTO, HttpServletRequest request) {
        Ticket ticket = ticketMapper.ticketRequestDTOToTicket(ticketRequestDTO);
        Flight flight = flightRepository.findById(ticketRequestDTO.getFlightId()).orElseThrow(() -> new AppException(ErrorCode.FLIGHT_NOT_EXISTED));
        Plane plane = flight.getPlane();
        int i = 0;
        List<Seat> seats = plane.getSeats();
        for(Seat seat : seats) {
            if (!seat.isBooked() && seat.getSeatType().equals(ticketRequestDTO.getSeatType())) {
                ticket.addSeat(seat);
                seat.addTicket(ticket);
                seat.setBooked(true);
                i++;
                if (i == ticketRequestDTO.getTotalPeople()) {
                    break;
                }
            }
        }
        ticket.setFlight(flight);
        ticket.setBookingDate(flight.getDepartureTime());
        ticket.setStatus(TicketStatus.PENDING.getDisplayName());
        ticket.setTicketNumber("BNovel" + VNPayUtil.getRandomNumber(6));
        ticket = ticketRepository.save(ticket);
        TicketResponseDTO ticketResponseDTO = ticketMapper.ticketToTicketResponseDTO(ticket);
        ticketResponseDTO.setPayUrl(getPayUrl(request, ticket.getPrice(), ticket.getTicketNumber()));
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, "delayed", ticket.getId());
        return ticketResponseDTO;
    }

    @Override
    public String updateTicketStatus(String ticketNumber, String status) {
        Ticket ticket = ticketRepository.findByTicketNumber(ticketNumber).orElseThrow(() -> new AppException(ErrorCode.TICKET_NOT_EXISTED));
        ticket.setStatus(status);
        ticketRepository.save(ticket);
        return ticket.getStatus();
    }

    @Override
    public PageApiResponse<List<TicketResponseDTO>> searchTicket(TicketSearchRequest ticketSearchRequest) {
        List<Ticket> tickets = ticketRepository.search(ticketSearchRequest);
        Long totalUsers = ticketRepository.count(ticketSearchRequest);
        List<TicketResponseDTO> ticketResponseDTOS = tickets.stream().map(ticketMapper::ticketToTicketResponseDTO).collect(Collectors.toList());
        PageApiResponse.PageableResponse pageableResponse = PageApiResponse.PageableResponse.builder()
                .pageSize(ticketSearchRequest.getPageSize())
                .pageIndex(ticketSearchRequest.getPageIndex())
                .totalElements(totalUsers)
                .totalPages((int)(Math.ceil((double)totalUsers / ticketSearchRequest.getPageSize())))
                .hasNext((ticketSearchRequest.getPageIndex() + 1) * ticketSearchRequest.getPageSize() < totalUsers)
                .hasPrevious(ticketSearchRequest.getPageIndex() >0).build();
        return PageApiResponse.<List<TicketResponseDTO>>builder()
                .data(ticketResponseDTOS)
                .success(true)
                .code(200)
                .pageable(pageableResponse)
                .message("Search tickets successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @Override
    public TicketResponseDTO getTicketByTicketNumber(String ticketNumber) {
        Ticket ticket = ticketRepository.findByTicketNumber(ticketNumber).orElseThrow(() -> new AppException(ErrorCode.TICKET_NOT_EXISTED));
        return ticketMapper.ticketToTicketResponseDTO(ticket);
    }

    private String getPayUrl(HttpServletRequest request, int totalPrice, String ticketNumber) {
        long amount = totalPrice * 100L;
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
        vnpParamsMap.put("vnp_TxnRef", ticketNumber);
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        return vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
    }
}
