package com.pragma.plazoleta.infrastructure.output.jpa.adapter;

import com.pragma.plazoleta.domain.model.User;
import com.pragma.plazoleta.domain.spi.IUserPersistencePort;
import com.pragma.plazoleta.infrastructure.exceptions.NoDataFoundException;
import com.pragma.plazoleta.infrastructure.exceptions.UserEmailAlreadyExistException;
import com.pragma.plazoleta.infrastructure.exceptions.UserNotFoundException;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.UserEntity;
import com.pragma.plazoleta.infrastructure.output.jpa.mapper.UserEntityMapper;
import com.pragma.plazoleta.infrastructure.output.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public void saveUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserEmailAlreadyExistException("User with email " + user.getEmail() + " already exists");
        }
        userRepository.save(userEntityMapper.toEntity(user));
    }

    @Override
    public User getUserById(Long id) {
        return userEntityMapper.toUser(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found")));
    }

    @Override
    public void updateUser(User user) {
        if (!userRepository.findById(user.getId()).isPresent()) {
            throw new UserNotFoundException("User not found");
        }
        userRepository.save(userEntityMapper.toEntity(user));
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.findById(id).isPresent()) {
            throw new UserNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new NoDataFoundException("No data found");
        }
        return userEntityMapper.toUserList(users);
    }

    @Override
    public List<User> getUsersByRoleId(Long roleId) {
        List<UserEntity> users = userRepository.findByRoleId(roleId);
        if (users.isEmpty()) {
            throw new NoDataFoundException("No data found");
        }
        return userEntityMapper.toUserList(users);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(userEntityMapper::toUser);
    }

}
