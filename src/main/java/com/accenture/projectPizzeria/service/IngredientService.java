package com.accenture.projectPizzeria.service;

import com.accenture.projectPizzeria.repository.model.Ingredient;
import com.accenture.projectPizzeria.service.dto.IngredientRequestDto;
import com.accenture.projectPizzeria.service.dto.IngredientResponseDto;

import java.util.List;

public interface IngredientService {

    IngredientResponseDto addIngredient(IngredientRequestDto ingredientRequestDto);
    List<IngredientResponseDto> getAllIngredients();
    IngredientResponseDto findIngredientByName(String name);

    void verifyIngredient(IngredientRequestDto ingredientRequestDto);
}
