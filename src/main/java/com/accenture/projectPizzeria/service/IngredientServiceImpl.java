package com.accenture.projectPizzeria.service;

import com.accenture.projectPizzeria.mapper.IngredientMapper;
import com.accenture.projectPizzeria.service.dto.IngredientRequestDto;
import com.accenture.projectPizzeria.service.dto.IngredientResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor

public class IngredientServiceImpl implements IngredientService {

    private final IngredientMapper ingredientMapper;

    @Override
    public IngredientResponseDto addIngredient(IngredientRequestDto ingredientRequestDto) {
        return null;
    }
}
