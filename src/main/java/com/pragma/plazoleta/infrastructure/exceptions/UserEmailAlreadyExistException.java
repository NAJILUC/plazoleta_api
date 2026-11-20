package com.pragma.plazoleta.infrastructure.exceptions;

public class UserEmailAlreadyExistException extends RuntimeException {
    public UserEmailAlreadyExistException(String message) {
        super(message);
    }
}
