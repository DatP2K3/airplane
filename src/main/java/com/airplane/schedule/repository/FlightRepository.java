package com.airplane.schedule.repository;

import com.airplane.schedule.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Integer>, FlightRepositoryCustom {
    @Query("SELECT f FROM Flight f " +
            "WHERE f.departureAirport.code = :departureCode " +
            "AND f.arrivalAirport.code = :arrivalCode " +
            "AND DATE(f.departureTime) = :departureDate")
    List<Flight> findFlightsByDeparture(
            @Param("departureCode") String departureCode,
            @Param("arrivalCode") String arrivalCode,
            @Param("departureDate") Date departureDate
    );

    // Lấy chuyến bay về (nếu là khứ hồi)
    @Query("SELECT f FROM Flight f " +
            "WHERE f.departureAirport.code = :arrivalCode " +
            "AND f.arrivalAirport.code = :departureCode " +
            "AND DATE(f.departureTime) = :returnDate")
    List<Flight> findFlightsByReturn(
            @Param("departureCode") String departureCode,
            @Param("arrivalCode") String arrivalCode,
            @Param("returnDate") Date returnDate
    );

    @Query("SELECT COUNT(f) FROM Flight f WHERE FUNCTION('DATE', f.departureTime) = CURRENT_DATE")
    int getTotalFlightsToday();
}
