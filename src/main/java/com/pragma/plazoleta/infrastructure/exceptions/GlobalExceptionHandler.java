package com.pragma.plazoleta.infrastructure.exceptions;

import com.pragma.plazoleta.application.dto.response.common.ErrorResponse;
import com.pragma.plazoleta.domain.exception.InvalidCredentialsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(
            InvalidCredentialsException exception) {

        ErrorCode errorCode = ErrorCode.INVALID_CREDENTIALS;

        ErrorResponse response = new ErrorResponse(
                errorCode.getCode(),
                errorCode.getDescription()
        );

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(response);
    }
}