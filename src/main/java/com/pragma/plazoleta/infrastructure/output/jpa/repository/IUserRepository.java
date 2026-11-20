package com.pragma.plazoleta.infrastructure.output.jpa.repository;

import com.pragma.plazoleta.infrastructure.output.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findById(Long id);

    Optional<UserEntity> findByEmail(String email);

    void deleteById(Long id);

    List<UserEntity> findByRoleId(Long roleId);
}
