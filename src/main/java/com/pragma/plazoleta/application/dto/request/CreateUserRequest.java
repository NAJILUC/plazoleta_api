package com.pragma.plazoleta.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateUserRequest {

    @NotBlank(message = "Campo obligatorio")
    private String firstName;
    @NotBlank(message = "Campo obligatorio")
    private String lastName;
    @NotBlank(message = "Campo obligatorio")
    @Pattern(
            regexp = "^[0-9]+$",
            message = "Solo permite valores numericos")
    private String identificationNumber;
    @NotBlank(message = "Campo obligatorio")
    @Size(max = 13, message = "Maximo 13 caracteres incluido el +")
    @Pattern(
            regexp = "^\\+?[0-9]+$",
            message = "Solo permite valores numericos y el signo + al inicio"
    )
    private String phoneNumber;
    @NotNull(message = "Campo obligatorio")
    private LocalDate birthDate;
    @NotBlank(message = "Campo obligatorio")
    @Email
    private String email;
    @NotBlank(message = "Campo obligatorio")
    private String password;
}
