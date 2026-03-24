package com.accenture.projectPizzeria.repository;

import com.accenture.projectPizzeria.repository.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IngredientDao extends JpaRepository<Ingredient, UUID> {
}
