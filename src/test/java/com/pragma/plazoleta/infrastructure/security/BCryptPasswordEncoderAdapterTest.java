
package com.pragma.plazoleta.infrastructure.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BCryptPasswordEncoderAdapterTest {

    private final BCryptPasswordEncoderAdapter passwordEncoder = new BCryptPasswordEncoderAdapter();

    @Test
    void shouldGenerateHashFromPassword() {
        // Arrange
        String rawPassword = "MyPassword123";

        // Act
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // Assert
        assertNotNull(encodedPassword);
        assertNotEquals(rawPassword, encodedPassword);
    }

    @Test
    void shouldGenerateDifferentHashesForSamePassword() {
        // Arrange
        String rawPassword = "MyPassword123";

        // Act
        String firstHash = passwordEncoder.encode(rawPassword);
        String secondHash = passwordEncoder.encode(rawPassword);

        // Assert
        assertNotEquals(firstHash, secondHash);
    }

    @Test
    void shouldReturnTrueWhenPasswordMatches() {
        // Arrange
        String rawPassword = "MyPassword123";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // Act
        boolean result = passwordEncoder.matches(
                rawPassword, encodedPassword
        );

        // Assert
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenPasswordDoesNotMatch() {
        // Arrange
        String rawPassword = "MyPassword123";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // Act
        boolean result = passwordEncoder.matches(
                "AnotherPassword123", encodedPassword
        );

        // Assert
        assertFalse(result);
    }
}
