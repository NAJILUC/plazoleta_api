package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.api.IUserServicePort;
import com.pragma.plazoleta.domain.model.User;
import com.pragma.plazoleta.domain.spi.security.IPasswordEncoderPort;
import com.pragma.plazoleta.domain.spi.IUserPersistencePort;

import java.util.List;
import java.util.Optional;

public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IPasswordEncoderPort passwordEncoderPort;

    public UserUseCase(IUserPersistencePort userPersistencePort, IPasswordEncoderPort passwordEncoderPort) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoderPort.encode(user.getPassword()));
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

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userPersistencePort.getUserByEmail(email);
    }
}