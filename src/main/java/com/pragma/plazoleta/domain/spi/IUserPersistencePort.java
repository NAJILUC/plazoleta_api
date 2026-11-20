package com.pragma.plazoleta.domain.spi;

import com.pragma.plazoleta.domain.model.User;

import java.util.List;

public interface IUserPersistencePort {

    void saveUser(User user);

    User getUserById(Long id);

    void updateUser(User user);

    void deleteUser(Long id);

    List<User> getAllUsers();

    List<User> getUsersByRoleId(Long roleId);

}
