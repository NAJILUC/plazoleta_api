package com.pragma.plazoleta.infrastructure.security;

import com.pragma.plazoleta.domain.model.security.TokenPayload;
import com.pragma.plazoleta.domain.spi.security.ITokenProviderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
public class JwtTokenProviderAdapter implements ITokenProviderPort {

    private final JwtEncoder jwtEncoder;

    @Value("${security.jwt.duration}")
    private Long jwtDuration;

    @Override
    public String generateToken(TokenPayload payload) {

        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(payload.getEmail())
                .issuedAt(now)
                .expiresAt(now.plus(jwtDuration, ChronoUnit.MINUTES))
                .claim("userId", payload.getUserId())
                .claim("role", payload.getRole())
                .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

        return jwtEncoder.encode(
                JwtEncoderParameters.from(header, claims)
        ).getTokenValue();
    }
}