package com.accenture.projectPizzeria.repository.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

/**
 * Entity representing a customer of the pizzeria application.
 * This entity is used by the persistence layer (JPA/Hibernate)
 * The data exchanged with the API layer must be done through dedicated DTOs.
 * The identifier is generated automatically using UUID in order to guarantee uniqueness across the system.
 */
@Entity
@Data
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String email;
    private boolean isVip;

    @OneToMany
    @JoinColumn
    private List<Order> previousOrder;

    public Customer() {
    }

    public Customer(String name, String email, boolean isVip, List<Order> previousOrder) {
        this.name = name;
        this.email = email;
        this.isVip = isVip;
        this.previousOrder = previousOrder;
    }

    public Customer(String name, String email, boolean isVip) {
        this.name = name;
        this.email = email;
        this.isVip = isVip;
    }
}
