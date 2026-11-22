
package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.exception.InvalidCredentialsException;
import com.pragma.plazoleta.domain.model.Role;
import com.pragma.plazoleta.domain.model.User;
import com.pragma.plazoleta.domain.model.security.TokenPayload;
import com.pragma.plazoleta.domain.spi.IUserPersistencePort;
import com.pragma.plazoleta.domain.spi.IRolePersistencePort;
import com.pragma.plazoleta.domain.spi.security.IPasswordEncoderPort;
import com.pragma.plazoleta.domain.spi.security.ITokenProviderPort;
import com.pragma.plazoleta.infrastructure.exceptions.NoDataFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IPasswordEncoderPort passwordEncoderPort;

    @Mock
    private IRolePersistencePort rolePersistencePort;

    @Mock
    private ITokenProviderPort tokenProviderPort;

    @InjectMocks
    private AuthUseCase authUseCase;

    private User user;
    private Role role;

    @BeforeEach
    void setUp() {
        user = new User(
                1L,
                "Juan",
                "Pérez",
                "123456789",
                "3001234567",
                LocalDate.of(1995, 5, 20),
                "usuario@correo.com",
                "hashed-password",
                LocalDateTime.now(),
                null,
                null,
                null,
                "system",
                2L
        );

        role = new Role(2L, "PROPIETARIO");
    }

    @Test
    void loginSuccessfully() {

        // Arrange
        String email = user.getEmail();
        String password = "Password123";
        String tokenExpected = "jwt-token";

        when(userPersistencePort.getUserByEmail(email))
                .thenReturn(Optional.of(user));

        when(passwordEncoderPort.matches(password, user.getPassword()))
                .thenReturn(true);

        when(rolePersistencePort.getRoleById(user.getRoleId()))
                .thenReturn(role);

        when(tokenProviderPort.generateToken(any(TokenPayload.class)))
                .thenReturn(tokenExpected);

        // Act
        String token = authUseCase.login(email, password);

        // Assert
        assertEquals(tokenExpected, token);

        ArgumentCaptor<TokenPayload> captor =
                ArgumentCaptor.forClass(TokenPayload.class);

        verify(tokenProviderPort).generateToken(captor.capture());

        TokenPayload payload = captor.getValue();

        assertEquals(user.getId(), payload.getUserId());
        assertEquals(user.getEmail(), payload.getEmail());
        assertEquals(role.getName(), payload.getRole());

        verify(userPersistencePort).getUserByEmail(email);
        verify(passwordEncoderPort).matches(password, user.getPassword());
        verify(rolePersistencePort).getRoleById(user.getRoleId());
    }

    @Test
    void loginFailsWhenUserDoesNotExist() {

        // Arrange
        String email = "inexistente@correo.com";

        when(userPersistencePort.getUserByEmail(email))
                .thenReturn(Optional.empty());

        // Act & Assert
        InvalidCredentialsException exception = assertThrows(
                InvalidCredentialsException.class,
                () -> authUseCase.login(email, "Password123")
        );

        assertEquals("Invalid credentials", exception.getMessage());

        verify(userPersistencePort).getUserByEmail(email);

        verifyNoInteractions(
                passwordEncoderPort,
                rolePersistencePort,
                tokenProviderPort
        );
    }

    @Test
    void loginFailsWhenUserIsDeleted() {

        // Arrange
        String email = user.getEmail();

        user.setDeletedAt(LocalDateTime.now());

        when(userPersistencePort.getUserByEmail(email))
                .thenReturn(Optional.of(user));

        // Act & Assert
        InvalidCredentialsException exception = assertThrows(
                InvalidCredentialsException.class,
                () -> authUseCase.login(email, "Password123")
        );

        assertEquals("Invalid credentials", exception.getMessage());

        verify(userPersistencePort).getUserByEmail(email);

        verifyNoInteractions(
                passwordEncoderPort,
                rolePersistencePort,
                tokenProviderPort
        );
    }

    @Test
    void loginFailsWhenPasswordIsIncorrect() {

        // Arrange
        String email = user.getEmail();
        String password = "WrongPassword";

        when(userPersistencePort.getUserByEmail(email))
                .thenReturn(Optional.of(user));

        when(passwordEncoderPort.matches(password, user.getPassword()))
                .thenReturn(false);

        // Act & Assert
        InvalidCredentialsException exception = assertThrows(
                InvalidCredentialsException.class,
                () -> authUseCase.login(email, password)
        );

        assertEquals("Invalid credentials", exception.getMessage());

        verify(userPersistencePort).getUserByEmail(email);
        verify(passwordEncoderPort).matches(password, user.getPassword());

        verifyNoInteractions(
                rolePersistencePort,
                tokenProviderPort
        );
    }

    @Test
    void loginFailsWhenRoleDoesNotExist() {

        // Arrange
        String email = user.getEmail();
        String password = "Password123";

        when(userPersistencePort.getUserByEmail(email))
                .thenReturn(Optional.of(user));

        when(passwordEncoderPort.matches(password, user.getPassword()))
                .thenReturn(true);

        when(rolePersistencePort.getRoleById(user.getRoleId()))
                .thenThrow(new NoDataFoundException("Role not found"));

        // Act & Assert
        assertThrows(
                NoDataFoundException.class,
                () -> authUseCase.login(email, password)
        );

        verify(userPersistencePort).getUserByEmail(email);
        verify(passwordEncoderPort).matches(password, user.getPassword());
        verify(rolePersistencePort).getRoleById(user.getRoleId());

        verifyNoInteractions(tokenProviderPort);
    }
}
