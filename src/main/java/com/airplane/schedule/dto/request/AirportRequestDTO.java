package com.airplane.schedule.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirportRequestDTO {
    private String name;
    private String city;
    private String country;
    private String code;
}
