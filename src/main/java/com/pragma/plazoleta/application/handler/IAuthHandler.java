package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.LoginRequest;
import com.pragma.plazoleta.application.dto.response.LoginResponse;

public interface IAuthHandler {

    LoginResponse login(LoginRequest loginRequest);
}
