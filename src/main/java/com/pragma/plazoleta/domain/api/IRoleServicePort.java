package com.pragma.plazoleta.domain.api;

import com.pragma.plazoleta.domain.model.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleServicePort {

    void saveRole(Role role);

    Role getRoleById(Long id);

    void updateRole(Role role);

    void deleteRole(Long id);

    List<Role> getAllRoles();

    Optional<Role> findRoleByName(String name);
}
