package com.pragma.plazoleta.infrastructure.output.jpa.adapter;

import com.pragma.plazoleta.domain.model.Role;
import com.pragma.plazoleta.domain.spi.IRolePersistencePort;
import com.pragma.plazoleta.infrastructure.output.jpa.mapper.RoleEntityMapper;
import com.pragma.plazoleta.infrastructure.output.jpa.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class RoleJpaAdapter implements IRolePersistencePort {

    private final IRoleRepository roleRepository;
    private final RoleEntityMapper roleEntityMapper;

    @Override
    public void saveRole(Role role) {
        roleRepository.save(roleEntityMapper.toEntity(role));
    }

    @Override
    public Role getRoleById(Long id) {
        return roleEntityMapper.toRole(roleRepository.findById(id).orElse(null));
    }

    @Override
    public void updateRole(Role role) {
        roleRepository.save(roleEntityMapper.toEntity(role));
    }

    @Override
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleEntityMapper.toRoleList(roleRepository.findAll());
    }

    @Override
    public Optional<Role> getRoleByName(String name) {
        return roleRepository.findByName(name).map(roleEntityMapper::toRole);
    }
}
