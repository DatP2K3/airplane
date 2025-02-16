package com.airplane.schedule.service.Impl;

import com.airplane.schedule.dto.request.PlaneAvailableRequestDTO;
import com.airplane.schedule.dto.request.PlaneRequestDTO;
import com.airplane.schedule.dto.response.PlaneResponseDTO;
import com.airplane.schedule.mapper.PlaneMapper;
import com.airplane.schedule.model.Plane;
import com.airplane.schedule.model.Seat;
import com.airplane.schedule.model.enums.ModelPlane;
import com.airplane.schedule.model.enums.SeatType;
import com.airplane.schedule.repository.PlaneRepository;
import com.airplane.schedule.service.PlaneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaneServiceImpl implements PlaneService {
    private final PlaneRepository planeRepository;
    private final PlaneMapper planeMapper;
    @Override
    public PlaneResponseDTO createPlane(PlaneRequestDTO planeRequestDTO) {
        Plane plane = planeMapper.planeRequestDTOToPlane(planeRequestDTO);
        if(planeRequestDTO.getModel().equals(ModelPlane.AP_A220.getDisplayName())) {
            for(int i = 1; i < 5; i++) {
                Seat seat = Seat.builder().seatType(SeatType.FIRST_CLASS.getDisplayName()).seatNumber(planeRequestDTO.getPlaneName() + "_F" + i).isBooked(false).build();
                plane.addSeat(seat);
            }
            for(int i = 5; i < 11; i++) {
                Seat seat = Seat.builder().seatType(SeatType.BUSINESS.getDisplayName()).seatNumber(planeRequestDTO.getPlaneName() + "_F" + i).isBooked(false).build();
                plane.addSeat(seat);
            }
            for(int i = 11; i < 23; i++) {
                Seat seat = Seat.builder().seatType(SeatType.ECONOMY.getDisplayName()).seatNumber(planeRequestDTO.getPlaneName() + "_F" + i).isBooked(false).build();
                plane.addSeat(seat);
            }
        } else if(planeRequestDTO.getModel().equals(ModelPlane.AP_A290.getDisplayName())) {
            for(int i = 1; i < 5; i++) {
                Seat seat = Seat.builder().seatType(SeatType.FIRST_CLASS.getDisplayName()).seatNumber(planeRequestDTO.getPlaneName() + "_F" + i).isBooked(false).build();
                plane.addSeat(seat);
            }
            for(int i = 5; i < 11; i++) {
                Seat seat = Seat.builder().seatType(SeatType.BUSINESS.getDisplayName()).seatNumber(planeRequestDTO.getPlaneName() + "_B" + i).isBooked(false).build();
                plane.addSeat(seat);
            }
            for(int i = 11; i < 30; i++) {
                Seat seat = Seat.builder().seatType(SeatType.ECONOMY.getDisplayName()).seatNumber(planeRequestDTO.getPlaneName() + "_E" + i).isBooked(false).build();
                plane.addSeat(seat);
            }
        } else if(planeRequestDTO.getModel().equals(ModelPlane.AP_A330.getDisplayName())) {
            for(int i = 1; i < 7; i++) {
                Seat seat = Seat.builder().seatType(SeatType.FIRST_CLASS.getDisplayName()).seatNumber(planeRequestDTO.getPlaneName() + "_" + i).isBooked(false).build();
                plane.addSeat(seat);
            }
            for(int i = 7; i < 14; i++) {
                Seat seat = Seat.builder().seatType(SeatType.BUSINESS.getDisplayName()).seatNumber(planeRequestDTO.getPlaneName() + "_B" + i).isBooked(false).build();
                plane.addSeat(seat);
            }
            for(int i = 14; i < 34; i++) {
                Seat seat = Seat.builder().seatType(SeatType.ECONOMY.getDisplayName()).seatNumber(planeRequestDTO.getPlaneName() + "_E" + i).isBooked(false).build();
                plane.addSeat(seat);
            }
        }
        return planeMapper.planeToPlaneResponseDTO(planeRepository.save(plane));
    }

    @Override
    public List<PlaneResponseDTO> getListPlaneavailable(PlaneAvailableRequestDTO planeAvailableRequestDTO) {
        List<Plane> planes = planeRepository.findAvailablePlanes(planeAvailableRequestDTO.getDepartureTime(), planeAvailableRequestDTO.getArrivalTime());
        List<PlaneResponseDTO> planeResponseDTOS = planes.stream().map(planeMapper::planeToPlaneResponseDTO).toList();
        return planeResponseDTOS;
    }
}
