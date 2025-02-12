package com.airplane.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightResponseDTO {
    private int id;
    private Date departureTime;
    private Date arrivalTime;
    private String departureAirportName;
    private String arrivalAirportName;
    private String planeModel;
    private String status;
    private int firstClassPrice;
    private int businessClassPrice;
    private int economyClassPrice;
    private Date createdAt;
    private Date updatedAt;
    private String createdBy;
    private String lastModifiedBy;
}
