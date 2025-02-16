package com.airplane.schedule.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

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

    @Column(name = "ticket_number")
    private String ticketNumber;

    @Column(name = "price")
    private int price;

    @Column(name = "booking_date")
    private Date bookingDate;

    @OneToOne(cascade = {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @OneToOne(cascade = {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "cccd")
    private String cccd;

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    private String status;

    @ManyToMany(mappedBy = "tickets", cascade = {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.DETACH})
    private List<Seat> seats = new ArrayList<>();

    public void addSeat(Seat seat){
        if(seats == null){
            seats = new ArrayList<>();
        }
        seats.add(seat);
    }
}
