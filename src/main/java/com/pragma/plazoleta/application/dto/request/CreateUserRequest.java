package com.pragma.plazoleta.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateUserRequest {

    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String identificationNumber;
    @NotEmpty
    private String phoneNumber;
    @NotNull
    private LocalDate birthDate;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String password;
}
