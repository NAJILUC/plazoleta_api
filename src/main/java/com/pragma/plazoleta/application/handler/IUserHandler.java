package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.CreateUserRequest;
import com.pragma.plazoleta.application.dto.response.UserResponse;

import java.util.List;

public interface IUserHandler {

    void saveUser(CreateUserRequest createUserRequest);

    UserResponse getUserById(Long id);

    void updateUser(Long id, CreateUserRequest createUserRequest);

    void deleteUser(Long id);

    List<UserResponse> getAllUsers();

}
