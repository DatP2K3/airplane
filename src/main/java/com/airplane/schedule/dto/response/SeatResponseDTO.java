package com.airplane.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeatResponseDTO {
    private int id;
    private String seatNumber;
    private boolean isAvailable;
}
