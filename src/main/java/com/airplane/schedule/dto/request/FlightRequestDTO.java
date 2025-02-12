package com.airplane.schedule.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlightRequestDTO {
    private Date departureTime;
    private Date arrivalTime;
    private String departureAirportCode;
    private String arrivalAirportCode;
    private int planeId;
    private String status;
    private int firstClassPrice;
    private int businessClassPrice;
    private int economyClassPrice;
}