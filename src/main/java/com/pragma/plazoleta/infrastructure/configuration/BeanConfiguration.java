package com.pragma.plazoleta.infrastructure.configuration;

import com.pragma.plazoleta.domain.api.IAuthServicePort;
import com.pragma.plazoleta.domain.api.IRoleServicePort;
import com.pragma.plazoleta.domain.api.IUserServicePort;
import com.pragma.plazoleta.domain.spi.IRolePersistencePort;
import com.pragma.plazoleta.domain.spi.IUserPersistencePort;
import com.pragma.plazoleta.domain.spi.security.IPasswordEncoderPort;
import com.pragma.plazoleta.domain.spi.security.ITokenProviderPort;
import com.pragma.plazoleta.domain.usecase.AuthUseCase;
import com.pragma.plazoleta.domain.usecase.RoleUseCase;
import com.pragma.plazoleta.domain.usecase.UserUseCase;
import com.pragma.plazoleta.infrastructure.output.jpa.adapter.RoleJpaAdapter;
import com.pragma.plazoleta.infrastructure.output.jpa.adapter.UserJpaAdapter;
import com.pragma.plazoleta.infrastructure.output.jpa.mapper.RoleEntityMapper;
import com.pragma.plazoleta.infrastructure.output.jpa.mapper.UserEntityMapper;
import com.pragma.plazoleta.infrastructure.output.jpa.repository.IRoleRepository;
import com.pragma.plazoleta.infrastructure.output.jpa.repository.IUserRepository;
import com.pragma.plazoleta.infrastructure.security.BCryptPasswordEncoderAdapter;
import com.pragma.plazoleta.infrastructure.security.JwtTokenProviderAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtEncoder;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IUserRepository userRepository;
    private final UserEntityMapper userEntityMapper;
    private final IRoleRepository roleRepository;
    private final RoleEntityMapper roleEntityMapper;
    private final JwtEncoder jwtEncoder;


    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserJpaAdapter(userRepository, userEntityMapper);
    }

    @Bean
    public IUserServicePort userServicePort(IUserPersistencePort userPersistencePort, IPasswordEncoderPort passwordEncoderPort) {
        return new UserUseCase(userPersistencePort, passwordEncoderPort);
    }

    @Bean
    public IRolePersistencePort rolePersistencePort() {
        return new RoleJpaAdapter(roleRepository, roleEntityMapper);
    }

    @Bean
    public IRoleServicePort roleServicePort(IRolePersistencePort rolePersistencePort) {
        return new RoleUseCase(rolePersistencePort);
    }

    @Bean
    public IPasswordEncoderPort passwordEncoderPort() {
        return new BCryptPasswordEncoderAdapter();
    }

    @Bean
    public ITokenProviderPort tokenProviderPort() {
        return new JwtTokenProviderAdapter(jwtEncoder);
    }

    @Bean
    public IAuthServicePort authServicePort(IUserPersistencePort userPersistencePort, IPasswordEncoderPort passwordEncoderPort,
                                            IRolePersistencePort rolePersistencePort, ITokenProviderPort tokenProviderPort) {

        return new AuthUseCase(userPersistencePort, passwordEncoderPort, rolePersistencePort, tokenProviderPort
        );
    }
}
