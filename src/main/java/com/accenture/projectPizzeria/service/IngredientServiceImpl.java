package com.accenture.projectPizzeria.service;

import com.accenture.projectPizzeria.exception.IngredientException;
import com.accenture.projectPizzeria.mapper.IngredientMapper;
import com.accenture.projectPizzeria.repository.IngredientDao;
import com.accenture.projectPizzeria.repository.model.Ingredient;
import com.accenture.projectPizzeria.service.dto.IngredientRequestDto;
import com.accenture.projectPizzeria.service.dto.IngredientResponseDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional

public class IngredientServiceImpl implements IngredientService {

    private final IngredientDao ingredientDao;
    private final IngredientMapper ingredientMapper;
    private final MessageSourceAccessor messages;

    public IngredientServiceImpl(IngredientDao ingredientDao, IngredientMapper ingredientMapper, MessageSourceAccessor messages) {
        this.ingredientDao = ingredientDao;
        this.ingredientMapper = ingredientMapper;
        this.messages = messages;
    }

    @Override
    public IngredientResponseDto addIngredient(IngredientRequestDto ingredientRequestDto) {

        verifyIngredient(ingredientRequestDto);
        Ingredient savedIngredient = ingredientDao.save(ingredientMapper.toIngredient(ingredientRequestDto));
        return ingredientMapper.toIngredientResponseDto(savedIngredient);

    }

    @Override
    public List<IngredientResponseDto> getAllIngredients() {
        List<Ingredient> savedIngredients = ingredientDao.findAll();
        return savedIngredients.stream()
                .map(ingredientMapper::toIngredientResponseDto)
                .toList();
    }

    @Override
    public IngredientResponseDto findIngredientByName(String name) {
        Ingredient ingredient = null;
        try {
            ingredient = ingredientDao.findByIngredientName(name.toUpperCase());
        } catch (EntityNotFoundException _) {
            throw new EntityNotFoundException(messages.getMessage("ingredient.not.found"));
        }
        return ingredientMapper.toIngredientResponseDto(ingredient);
    }

    @Override
    public void verifyIngredient(IngredientRequestDto ingredientRequestDto) {
        if(ingredientRequestDto == null)
            throw new IngredientException(messages.getMessage("ingredient.null"));
    }

}
