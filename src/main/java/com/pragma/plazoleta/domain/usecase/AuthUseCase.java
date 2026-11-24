package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.api.IAuthServicePort;
import com.pragma.plazoleta.domain.exception.InvalidCredentialsException;
import com.pragma.plazoleta.domain.model.Role;
import com.pragma.plazoleta.domain.model.User;
import com.pragma.plazoleta.domain.model.security.TokenPayload;
import com.pragma.plazoleta.domain.spi.IRolePersistencePort;
import com.pragma.plazoleta.domain.spi.IUserPersistencePort;
import com.pragma.plazoleta.domain.spi.security.IPasswordEncoderPort;
import com.pragma.plazoleta.domain.spi.security.ITokenProviderPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthUseCase implements IAuthServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IPasswordEncoderPort passwordEncoderPort;
    private final IRolePersistencePort rolePersistencePort;
    private final ITokenProviderPort tokenProviderPort;

    @Override
    public String login(String email, String password) {

        User user = userPersistencePort.getUserByEmail(email)
                .filter(existingUser -> existingUser.getDeletedAt() == null)
                .orElseThrow(() ->
                        new InvalidCredentialsException("Invalid credentials"));

        if (!passwordEncoderPort.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        Role role = rolePersistencePort.getRoleById(user.getRoleId());

        TokenPayload payload = new TokenPayload(
                user.getId(),
                user.getEmail(),
                role.getName()
        );

        return tokenProviderPort.generateToken(payload);
    }
}