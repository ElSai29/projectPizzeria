package com.accenture.projectPizzeria.repository.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Entity representing a pizza.
 * A pizza corresponds to a product that can be ordered by a client.
 * It is defined by its name, a list of ingredients and a map containing the sizes and the prices.
 * It is persisted in the PIZZAS table and calls for INGREDIENTS table.
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PIZZAS")

public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    String name;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "PizzaIngredient",
                joinColumns = {@JoinColumn(name = "pizzaId")},
                inverseJoinColumns = {@JoinColumn(name = "ingredientId")})
    private List<Ingredient> ingredientList;

    private HashMap<PizzaSize, Double> prices;

    public Pizza(String name, List<Ingredient> ingredientList, HashMap<PizzaSize, Double> prices) {
        this.name = name;
        this.ingredientList = ingredientList;
        this.prices = prices;
    }
}
