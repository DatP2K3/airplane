package com.airplane.schedule.repository;

import com.airplane.schedule.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Integer>, TicketRepositoryCustom {
    Optional<Ticket> findByTicketNumber(String txnRef);

    @Query("SELECT COALESCE(SUM(t.price), 0) FROM Ticket t WHERE FUNCTION('DATE', t.bookingDate) = CURRENT_DATE AND t.status = 'Success'")
    Long getTotalRevenueToday();

    @Query("SELECT COUNT(t) FROM Ticket t WHERE FUNCTION('DATE', t.bookingDate) = CURRENT_DATE AND t.status = 'Success'")
    int getTotalTicketsToday();

    @Query("SELECT t FROM Ticket t WHERE t.user.id = :userId")
    List<Ticket> findTicketsByUserId(@Param("userId") int userId);
}
