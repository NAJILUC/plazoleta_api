package com.pragma.plazoleta.application.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String identificationNumber;
    private String phoneNumber;
    private LocalDate birthDate;
    private String email;
    private RoleResponse roleResponse;

}
