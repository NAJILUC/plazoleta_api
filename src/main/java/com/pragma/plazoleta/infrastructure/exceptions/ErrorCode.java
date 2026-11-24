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
    AUTHENTICATION_REQUIRED("SE03", "Se requiere una autenticación válida", HttpStatus.UNAUTHORIZED),

    // Usuarios
    USER_EMAIL_ALREADY_EXISTS("US01", "El correo electrónico del usuario ya existe", HttpStatus.BAD_REQUEST),
    INVALID_FIELDS("US02", "Campos inválidos", HttpStatus.BAD_REQUEST),
    MINOR_USER("US03", "El usuario debe ser mayor de edad", HttpStatus.BAD_REQUEST),
    USER_NOT_OWNER("US04", "El usuario no tiene el rol de propietario", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String description;
    private final HttpStatus httpStatus;
}