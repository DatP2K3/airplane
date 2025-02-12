package com.airplane.schedule.mapper;

import com.airplane.schedule.dto.request.PlaneRequestDTO;
import com.airplane.schedule.dto.response.PlaneResponseDTO;
import com.airplane.schedule.model.Plane;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlaneMapper {
    Plane planeRequestDTOToPlane(PlaneRequestDTO planeRequestDTO);
    PlaneResponseDTO planeToPlaneResponseDTO(Plane plane);
}
