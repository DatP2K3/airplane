package com.airplane.schedule.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketResponseDTO {
    private int id;
    private String ticketNumber;
    private Date bookingDate;
    private int flightId;
    private List<String> seatNumbers;
    private String fullName;
    private String email;
    private String phone;
    private String status;
    private String payUrl;
}
