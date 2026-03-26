package com.accenture.projectPizzeria.service.dto;

import com.accenture.projectPizzeria.repository.model.PizzaSize;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public record PizzaResponseDto(

        UUID id, String name, List<IngredientResponseDto> ingredientList, HashMap<PizzaSize, Double> prices

) {
}
