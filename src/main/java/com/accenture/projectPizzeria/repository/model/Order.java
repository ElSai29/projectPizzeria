package com.accenture.projectPizzeria.repository.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "ORDERS")
public class Order {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private int orderPrice;

    public Order(int orderPrice) {
        this.orderPrice = orderPrice;
    }
}
