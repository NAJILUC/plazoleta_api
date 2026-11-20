package com.pragma.plazoleta.infrastructure.output.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users",
        indexes = {
                @Index(
                        name = "idx_users_email",
                        columnList = "email",
                        unique = true
                )
        })
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String identificationNumber;
    private String phoneNumber;
    private LocalDate birthDate;
    private String email;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private String deletedBy;
    private String createdBy;

    private Long roleId;
}
