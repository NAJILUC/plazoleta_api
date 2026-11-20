package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.api.IRoleServicePort;
import com.pragma.plazoleta.domain.model.Role;
import com.pragma.plazoleta.domain.spi.IRolePersistencePort;

import java.util.List;

public class RoleUseCase implements IRoleServicePort {

    private final IRolePersistencePort rolePersistencePort;

    public RoleUseCase(IRolePersistencePort rolePersistencePort) {
        this.rolePersistencePort = rolePersistencePort;
    }

    @Override
    public void saveRole(Role role) {
        rolePersistencePort.saveRole(role);
    }

    @Override
    public Role getRoleById(Long id) {
        return rolePersistencePort.getRoleById(id);
    }

    @Override
    public void updateRole(Role role) {
        rolePersistencePort.updateRole(role);
    }

    @Override
    public void deleteRole(Long id) {
        rolePersistencePort.deleteRole(id);
    }

    @Override
    public List<Role> getAllRoles() {
        return rolePersistencePort.getAllRoles();
    }
}
