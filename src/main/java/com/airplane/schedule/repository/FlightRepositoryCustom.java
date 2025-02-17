package com.airplane.schedule.repository;

import com.airplane.schedule.dto.request.FlightSearchRequest;
import com.airplane.schedule.model.Flight;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FlightRepositoryCustom {
    List<Flight> search(FlightSearchRequest flightSearchRequest);
    Long count(FlightSearchRequest flightSearchRequest);
}
