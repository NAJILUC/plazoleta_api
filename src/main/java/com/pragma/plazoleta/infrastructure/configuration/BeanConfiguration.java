package com.pragma.plazoleta.infrastructure.configuration;

import com.pragma.plazoleta.domain.api.IRoleServicePort;
import com.pragma.plazoleta.domain.api.IUserServicePort;
import com.pragma.plazoleta.domain.spi.IRolePersistencePort;
import com.pragma.plazoleta.domain.spi.IUserPersistencePort;
import com.pragma.plazoleta.domain.usecase.RoleUseCase;
import com.pragma.plazoleta.domain.usecase.UserUseCase;
import com.pragma.plazoleta.infrastructure.output.jpa.adapter.RoleJpaAdapter;
import com.pragma.plazoleta.infrastructure.output.jpa.adapter.UserJpaAdapter;
import com.pragma.plazoleta.infrastructure.output.jpa.mapper.RoleEntityMapper;
import com.pragma.plazoleta.infrastructure.output.jpa.mapper.UserEntityMapper;
import com.pragma.plazoleta.infrastructure.output.jpa.repository.IRoleRepository;
import com.pragma.plazoleta.infrastructure.output.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IUserRepository userRepository;
    private final UserEntityMapper userEntityMapper;
    private final IRoleRepository roleRepository;
    private final RoleEntityMapper roleEntityMapper;

    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserJpaAdapter(userRepository, userEntityMapper);
    }

    @Bean
    public IUserServicePort userServicePort(){
        return new UserUseCase(userPersistencePort());
    }

    @Bean
    public IRolePersistencePort rolePersistencePort() {
        return new RoleJpaAdapter(roleRepository, roleEntityMapper);
    }

    @Bean
    public IRoleServicePort roleServicePort() {
        return new RoleUseCase(rolePersistencePort());
    }
}
