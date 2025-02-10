package com.airplane.schedule.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.HashSet;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private int id;

    @Column(name = "price")
    private double price;

    @Column(name = "booking_date")
    private String bookingDate;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "ticket_status")
    private String ticketStatus;

    @OneToOne(cascade = {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @OneToOne(cascade = {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "ticket", cascade = {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.DETACH})
    private HashSet<Seat> seats;
}
