package com.accenture.projectPizzeria.mapper;

import com.accenture.projectPizzeria.repository.model.Ingredient;
import com.accenture.projectPizzeria.service.dto.IngredientRequestDto;
import com.accenture.projectPizzeria.service.dto.IngredientResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface IngredientMapper {

    Ingredient toIngredient(IngredientRequestDto ingredientRequestDto);
    IngredientResponseDto toIngredientResponseDto(Ingredient ingredient);

}
