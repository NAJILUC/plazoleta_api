package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.LoginRequest;
import com.pragma.plazoleta.application.dto.response.LoginResponse;
import com.pragma.plazoleta.domain.api.IAuthServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthHandler implements IAuthHandler {

    private final IAuthServicePort authServicePort;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        String token = authServicePort.login(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );

        LoginResponse response = new LoginResponse();
        response.setToken(token);

        return response;
    }
}