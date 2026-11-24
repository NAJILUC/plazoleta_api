package com.pragma.plazoleta.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRestaurantRequest {

    @NotBlank(message = "Campo obligatorio")
    private String name;
    @NotBlank(message = "Campo obligatorio")
    @Pattern(
            regexp = "^[0-9]+$",
            message = "Solo permite valores numericos")
    private String nit;
    @NotBlank(message = "Campo obligatorio")
    private String address;
    @NotBlank(message = "Campo obligatorio")
    @Size(max = 13, message = "Maximo 13 caracteres incluido el +")
    @Pattern(
            regexp = "^\\+?[0-9]+$",
            message = "Solo permite valores numericos y el signo + al inicio"
    )
    private String phoneNumber;
    @NotBlank(message = "Campo obligatorio")
    private String urlLogo;
    @NotNull(message = "Campo obligatorio")
    private Long ownerId;
}
