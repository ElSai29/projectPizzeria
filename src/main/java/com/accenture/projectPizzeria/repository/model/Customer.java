package com.accenture.projectPizzeria.repository.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    public Customer(String name, String email, boolean isVip, List<Order> previousOrder) {
        this.name = name;
        this.email = email;
        this.isVip = isVip;
        this.previousOrder = previousOrder;
    }
}
