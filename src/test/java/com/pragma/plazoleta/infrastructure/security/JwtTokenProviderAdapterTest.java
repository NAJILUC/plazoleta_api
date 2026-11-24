
package com.pragma.plazoleta.infrastructure.security;

import com.pragma.plazoleta.domain.model.security.TokenPayload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtTokenProviderAdapterTest {

    @Mock
    private JwtEncoder jwtEncoder;

    private JwtTokenProviderAdapter jwtTokenProviderAdapter;

    @BeforeEach
    void setUp() {
        jwtTokenProviderAdapter = new JwtTokenProviderAdapter(jwtEncoder);

        ReflectionTestUtils.setField(
                jwtTokenProviderAdapter,
                "jwtDuration",
                15L
        );
    }

    @Test
    void generateTokenSuccessfully() {

        // Arrange
        TokenPayload payload = new TokenPayload(
                1L,
                "usuario@correo.com",
                "ADMINISTRADOR"
        );

        Jwt jwt = Jwt.withTokenValue("token-de-prueba")
                .header("alg", "HS256")
                .claim("sub", payload.getEmail())
                .build();

        when(jwtEncoder.encode(any(JwtEncoderParameters.class)))
                .thenReturn(jwt);

        // Act
        String token = jwtTokenProviderAdapter.generateToken(payload);

        // Assert
        assertEquals("token-de-prueba", token);

        ArgumentCaptor<JwtEncoderParameters> captor =
                ArgumentCaptor.forClass(JwtEncoderParameters.class);

        verify(jwtEncoder, times(1)).encode(captor.capture());

        JwtEncoderParameters parameters = captor.getValue();
        JwtClaimsSet claims = parameters.getClaims();

        assertEquals(payload.getEmail(), claims.getSubject());
        assertEquals(payload.getUserId(), claims.getClaim("userId"));
        assertEquals(payload.getRole(), claims.getClaim("role"));

        assertNotNull(claims.getIssuedAt());
        assertNotNull(claims.getExpiresAt());
        assertTrue(claims.getExpiresAt().isAfter(claims.getIssuedAt()));

        Duration tokenDuration = Duration.between(
                claims.getIssuedAt(),
                claims.getExpiresAt()
        );

        assertEquals(Duration.ofMinutes(15), tokenDuration);

        assertEquals(
                "HS256",
                parameters.getJwsHeader().getAlgorithm().getName()
        );
    }
}
