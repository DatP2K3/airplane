package com.airplane.schedule.repository;

import com.airplane.schedule.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport, Integer> {
    Airport findByCode(String code);
}
