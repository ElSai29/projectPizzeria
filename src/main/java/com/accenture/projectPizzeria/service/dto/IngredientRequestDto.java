package com.accenture.projectPizzeria.service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record IngredientRequestDto(

        @NotBlank(message = "ingredient.name.null") String ingredientName,
        @NotNull(message = "ingredient.stock.null")
        @Min(value = 0, message = "ingredient.stock.negative") int stock

) {
}
