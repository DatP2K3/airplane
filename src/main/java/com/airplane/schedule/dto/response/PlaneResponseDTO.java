package com.airplane.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaneResponseDTO {
    private int id;
    private String planeName;
    private String model;
    private Date createdAt;
    private Date updatedAt;
    private String createdBy;
    private String lastModifiedBy;
    private List<SeatResponseDTO> flights;
}
