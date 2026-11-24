package com.pragma.plazoleta.infrastructure.output.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "restaurants")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String nit;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false, length = 13)
    private String phoneNumber;
    @Column(nullable = false)
    private String urlLogo;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;
}
