package com.accenture.projectPizzeria.service.dto;

import com.accenture.projectPizzeria.repository.model.PizzaSize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.HashMap;
import java.util.List;

public record PizzaRequestDto(

        @NotBlank(message = "pizza.name.null") String name,
        @NotNull(message = "pizza.ingredients.null") List<String> ingredientsName,
        @NotNull(message = "size.price.null") HashMap<PizzaSize, Double> prices

        ) {
}
