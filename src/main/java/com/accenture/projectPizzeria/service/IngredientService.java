package com.accenture.projectPizzeria.service;

import com.accenture.projectPizzeria.service.dto.IngredientRequestDto;
import com.accenture.projectPizzeria.service.dto.IngredientResponseDto;

import java.util.List;
import java.util.UUID;

public interface IngredientService {

    IngredientResponseDto addIngredient(IngredientRequestDto ingredientRequestDto);

    List<IngredientResponseDto> getAllIngredients();
    IngredientResponseDto findIngredientByName(String name);
    IngredientResponseDto findIngredientById(UUID id);

    IngredientResponseDto updateIngredientStock(String name, Integer stock);

    void verifyIngredient(IngredientRequestDto ingredientRequestDto);
}
