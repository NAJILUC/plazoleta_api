package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.CreateUserRequest;
import com.pragma.plazoleta.application.dto.response.UserResponse;
import com.pragma.plazoleta.application.mapper.request.UserRequestMapper;
import com.pragma.plazoleta.application.mapper.response.RoleResponseMapper;
import com.pragma.plazoleta.application.mapper.response.UserResponseMapper;
import com.pragma.plazoleta.domain.api.IRoleServicePort;
import com.pragma.plazoleta.domain.api.IUserServicePort;
import com.pragma.plazoleta.domain.model.Role;
import com.pragma.plazoleta.domain.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserHandler implements IUserHandler {

    private final IUserServicePort userServicePort;
    private final IRoleServicePort roleServicePort;
    private final UserRequestMapper userRequestMapper;
    private final UserResponseMapper userResponseMapper;
    private final RoleResponseMapper roleResponseMapper;

    @Override
    public void saveUser(CreateUserRequest createUserRequest) {
        Role role = roleServicePort.getRoleById(1L);
        User user = userRequestMapper.toUser(createUserRequest);
        user.setRoleId(role.getId());
        userServicePort.saveUser(user);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userServicePort.getUserById(id);
        return userResponseMapper.toUserResponse(user, user.getRoleId() != null ? roleServicePort.getRoleById(user.getRoleId()) : null);
    }

    @Override
    public void updateUser(Long id, CreateUserRequest createUserRequest) {
        User user = userServicePort.getUserById(id);
        user.setFirstName(createUserRequest.getFirstName());
        user.setLastName(createUserRequest.getLastName());
        user.setIdentificationNumber(createUserRequest.getIdentificationNumber());
        user.setPhoneNumber(createUserRequest.getPhoneNumber());
        user.setBirthDate(createUserRequest.getBirthDate());
        user.setEmail(createUserRequest.getEmail());
        user.setPassword(createUserRequest.getPassword());
        userServicePort.updateUser(user);
    }

    @Override
    public void deleteUser(Long id) {
        userServicePort.deleteUser(id);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userResponseMapper.toUserResponseList(userServicePort.getAllUsers(), roleServicePort.getAllRoles());
    }
}
