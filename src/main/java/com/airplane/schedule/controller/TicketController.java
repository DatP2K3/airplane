package com.airplane.schedule.controller;

import com.airplane.schedule.config.VNPAYConfig;
import com.airplane.schedule.dto.ApiResponse;
import com.airplane.schedule.dto.PageApiResponse;
import com.airplane.schedule.dto.request.TicketRequestDTO;
import com.airplane.schedule.dto.request.TicketSearchRequest;
import com.airplane.schedule.dto.response.TicketResponseDTO;
import com.airplane.schedule.dto.response.UserResponseDTO;
import com.airplane.schedule.model.Ticket;
import com.airplane.schedule.model.enums.TicketStatus;
import com.airplane.schedule.service.TicketService;
import com.airplane.schedule.util.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final VNPAYConfig vnPayConfig;

    @PostMapping("/tickets")
    public ApiResponse<TicketResponseDTO> createTicket(@RequestBody TicketRequestDTO ticketRequestDTO, HttpServletRequest request) {
        TicketResponseDTO ticketResponseDTO = ticketService.createTicket(ticketRequestDTO, request);
        return ApiResponse.<TicketResponseDTO>builder()
                .data(ticketResponseDTO)
                .success(true)
                .code(201)
                .message("Ticket created successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PatchMapping("/tickets/status/{ticketNumber}")
    public ApiResponse<String> ticketHandler(@PathVariable String ticketNumber, @RequestParam String status) {
        String statusTicket = ticketService.updateTicketStatus(ticketNumber, status);
        return ApiResponse.<String>builder()
                .data(statusTicket)
                .success(true)
                .code(200)
                .message("Update ticket status successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PostMapping("/tickets/search")
    public ResponseEntity<PageApiResponse<List<TicketResponseDTO>>> searchTicket(@RequestBody TicketSearchRequest ticketSearchRequest) {
        PageApiResponse<List<TicketResponseDTO>> response = ticketService.searchTicket(ticketSearchRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tickets/{ticketNumber}")
    public ApiResponse<TicketResponseDTO> getTicketByTicketNumber(@PathVariable String ticketNumber) {
        TicketResponseDTO ticketResponseDTO = ticketService.getTicketByTicketNumber(ticketNumber);
        return ApiResponse.<TicketResponseDTO>builder()
                .data(ticketResponseDTO)
                .success(true)
                .code(200)
                .message("Ticket retrieved successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }
}
