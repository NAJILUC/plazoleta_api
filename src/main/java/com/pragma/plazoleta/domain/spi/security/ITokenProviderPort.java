package com.pragma.plazoleta.domain.spi.security;

import com.pragma.plazoleta.domain.model.security.TokenPayload;

public interface ITokenProviderPort {

    String generateToken(TokenPayload payload);

}