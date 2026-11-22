package com.pragma.plazoleta.infrastructure.security;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.pragma.plazoleta.domain.spi.IPasswordEncoderPort;

public class BCryptPasswordEncoderAdapter implements IPasswordEncoderPort {

    @Override
    public String encode(String rawPassword) {
        return BCrypt.withDefaults().hashToString(12, rawPassword.toCharArray());
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword.toCharArray()).verified;
    }
}
