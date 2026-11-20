package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.api.IUserServicePort;
import com.pragma.plazoleta.domain.model.User;
import com.pragma.plazoleta.domain.spi.IUserPersistencePort;

import java.util.List;

public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;

    public UserUseCase(IUserPersistencePort userPersistencePort) {
        this.userPersistencePort = userPersistencePort;
    }

    @Override
    public void saveUser(User user) {
        userPersistencePort.saveUser(user);
    }

    @Override
    public User getUserById(Long id) {
        return userPersistencePort.getUserById(id);
    }

    @Override
    public void updateUser(User user) {
        userPersistencePort.updateUser(user);
    }

    @Override
    public void deleteUser(Long id) {
        userPersistencePort.deleteUser(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userPersistencePort.getAllUsers();
    }

    @Override
    public List<User> getUsersByRoleId(Long roleId) {
        return userPersistencePort.getUsersByRoleId(roleId);
    }
}