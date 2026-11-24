
package com.pragma.plazoleta.infrastructure.configuration;

import com.pragma.plazoleta.domain.api.IRoleServicePort;
import com.pragma.plazoleta.domain.api.IUserServicePort;
import com.pragma.plazoleta.domain.model.Role;
import com.pragma.plazoleta.domain.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminInitializer implements ApplicationRunner {

    private static final String ADMIN_ROLE = "ADMINISTRADOR";

    private final IUserServicePort userServicePort;
    private final IRoleServicePort roleServicePort;

    @Value("${app.admin.first-name}")
    private String firstName;

    @Value("${app.admin.last-name}")
    private String lastName;

    @Value("${app.admin.identification-number}")
    private String identificationNumber;

    @Value("${app.admin.phone-number}")
    private String phoneNumber;

    @Value("${app.admin.birth-date}")
    private LocalDate birthDate;

    @Value("${app.admin.email}")
    private String email;

    @Value("${app.admin.password}")
    private String password;

    @Override
    public void run(ApplicationArguments args) {

        Role adminRole = roleServicePort.findRoleByName(ADMIN_ROLE)
                .orElseThrow(() -> new IllegalStateException(
                        "No existe el rol ADMINISTRADOR en la base de datos"
                ));

        Optional<User> existingAdmin = userServicePort.getUserByEmail(email);

        if (existingAdmin.isPresent()) {
            if (!adminRole.getId().equals(existingAdmin.get().getRoleId())) {
                throw new IllegalStateException(
                        "El correo configurado para el administrador ya pertenece a otro rol"
                );
            }

            log.info("La cuenta administradora ya existe. No se requiere inicialización.");
            return;
        }

        User admin = new User(
                null,
                firstName,
                lastName,
                identificationNumber,
                phoneNumber,
                birthDate,
                email,
                password,
                null,
                null,
                null,
                null,
                "SYSTEM",
                adminRole.getId()
        );

        userServicePort.saveUser(admin);

        log.info("La cuenta administradora fue inicializada correctamente.");
    }
}
