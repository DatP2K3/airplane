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
public class AirportResponseDTO {
    private int id;
    private String name;
    private String city;
    private String country;
    private String code;
    private Date createdAt;
    private Date updatedAt;
    private String createdBy;
    private String lastModifiedBy;
}
