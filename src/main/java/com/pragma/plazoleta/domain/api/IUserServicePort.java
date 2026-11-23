package com.pragma.plazoleta.domain.api;

import com.pragma.plazoleta.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserServicePort {

    void saveUser(User user);

    User getUserById(Long id);

    void updateUser(User user);

    void deleteUser(Long id);

    List<User> getAllUsers();

    List<User> getUsersByRoleId(Long roleId);

    Optional<User> getUserByEmail(String email);

}
