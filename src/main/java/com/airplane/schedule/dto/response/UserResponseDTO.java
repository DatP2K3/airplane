package com.airplane.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    int id;
    String firstName;
    String lastName;
    String birthDate;
    String phone;
    String address;
    String email;
    String avatar;
    int roleId;
}
//dto
//mapper
//lombok
