package com.airplane.schedule.dto.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequestDTO {
    private int price;
    private int flightId;
    private int userId;
    private HashSet<Integer> seatIds;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Date dob;
    private String cccd;
    private String address;
    private String seatType;
    private int totalPeople;
}
