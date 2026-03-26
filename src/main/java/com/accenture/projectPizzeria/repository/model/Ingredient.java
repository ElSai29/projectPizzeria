package com.accenture.projectPizzeria.repository.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Entity representing an ingredient.
 * An ingredient corresponds to a product that can be used in the preparation
 * of pizzas.
 * An ingredient is defined by a name and a stock value.
 * It is persisted in the table INGREDIENTS.
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "INGREDIENTS")

public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    String ingredientName;
    int stock;

    public Ingredient(String ingredientName, int stock) {
        this.ingredientName = ingredientName;
        this.stock = stock;
    }

}
