package com.accenture.projectPizzeria.service.dto;

import java.util.UUID;

public record IngredientResponseDto(

        UUID id, String ingredientName, Integer stock

) {
}
