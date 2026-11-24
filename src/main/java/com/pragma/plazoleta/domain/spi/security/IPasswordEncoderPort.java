package com.pragma.plazoleta.domain.spi.security;

public interface IPasswordEncoderPort {

    String encode(String rawPassword);

    boolean matches(String rawPassword, String encodedPassword);
}
