package com.accenture.projectPizzeria.repository.model;

import jakarta.persistence.*;
import jakarta.xml.bind.PrintConversionEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JoinFormula;
import org.hibernate.engine.jdbc.Size;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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
    private List<Ingredient> ingredients;

    private HashMap<PizzaSize, Double> prices;

}
