package com.pragma.plazoleta.infrastructure.exceptions;

import com.pragma.plazoleta.application.dto.response.common.ErrorResponse;
import com.pragma.plazoleta.domain.exception.InvalidCredentialsException;
import com.pragma.plazoleta.domain.exception.MinorUserException;
import com.pragma.plazoleta.domain.exception.UserNotOwnerException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials() {

        ErrorCode errorCode = ErrorCode.INVALID_CREDENTIALS;

        ErrorResponse response = new ErrorResponse(
                errorCode.getCode(),
                errorCode.getDescription()
        );

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(response);
    }

    @ExceptionHandler(UserEmailAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleUserEmailAlreadyExist(
            UserEmailAlreadyExistException exception) {

        ErrorCode errorCode = ErrorCode.USER_EMAIL_ALREADY_EXISTS;

        ErrorResponse response = new ErrorResponse(
                errorCode.getCode(),
                errorCode.getDescription()
        );

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException exception) {

        Map<String, String> fields = new LinkedHashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        fields.putIfAbsent(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        ErrorCode errorCode = ErrorCode.INVALID_FIELDS;

        ErrorResponse response = new ErrorResponse(
                errorCode.getCode(),
                errorCode.getDescription(),
                fields
        );

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(response);
    }

    @ExceptionHandler(MinorUserException.class)
    public ResponseEntity<ErrorResponse> handleMinorUser(
            MinorUserException exception) {

        ErrorCode errorCode = ErrorCode.MINOR_USER;

        ErrorResponse response = new ErrorResponse(
                errorCode.getCode(),
                errorCode.getDescription()
        );

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(response);
    }

    @ExceptionHandler(UserNotOwnerException.class)
    public ResponseEntity<ErrorResponse> handleUserNotOwner(
            UserNotOwnerException exception) {

        ErrorCode errorCode = ErrorCode.USER_NOT_OWNER;

        ErrorResponse response = new ErrorResponse(
                errorCode.getCode(),
                errorCode.getDescription()
        );

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(response);
    }
}