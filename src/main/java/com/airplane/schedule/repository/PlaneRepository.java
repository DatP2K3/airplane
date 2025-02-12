package com.airplane.schedule.repository;

import com.airplane.schedule.model.Plane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PlaneRepository extends JpaRepository<Plane, Integer> {
    @Query("SELECT p FROM Plane p WHERE p.id NOT IN (" +
            "SELECT f.plane.id FROM Flight f " +
            "WHERE (f.departureTime <= :newArrivalTime AND f.arrivalTime >= :newDepartureTime))")
    List<Plane> findAvailablePlanes(@Param("newDepartureTime") Date newDepartureTime,
                                    @Param("newArrivalTime") Date newArrivalTime);
}
