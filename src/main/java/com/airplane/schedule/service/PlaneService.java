package com.airplane.schedule.service;

import com.airplane.schedule.dto.request.PlaneAvailableRequestDTO;
import com.airplane.schedule.dto.request.PlaneRequestDTO;
import com.airplane.schedule.dto.response.PlaneResponseDTO;

import java.util.List;

public interface PlaneService {
    PlaneResponseDTO createPlane(PlaneRequestDTO planeRequestDTO);
    List<PlaneResponseDTO> getListPlaneavailable(PlaneAvailableRequestDTO planeAvailableRequestDTO);
}
