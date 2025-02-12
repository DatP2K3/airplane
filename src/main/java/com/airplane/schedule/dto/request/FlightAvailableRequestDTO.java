package com.airplane.schedule.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightAvailableRequestDTO {
    private Date departureDay;
    private Date returnDay;
    private String departureAirportCode;
    private String arrivalAirportCode;
    private boolean isRoundTrip;
}
