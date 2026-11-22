package com.pragma.plazoleta.infrastructure.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Seguridad
    INVALID_CREDENTIALS("SE01", "Credenciales inválidas", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED("SE02", "No tienes permisos para realizar esta operación", HttpStatus.FORBIDDEN),
    AUTHENTICATION_REQUIRED("SE03", "Se requiere una autenticación válida", HttpStatus.UNAUTHORIZED);

    private final String code;
    private final String description;
    private final HttpStatus httpStatus;
}