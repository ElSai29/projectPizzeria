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
import java.util.UUID;

/**
 * Service handling bsuiness logic related to ingredients.
 * This service is responsible for managing ingredients, including creation,
 * retrieval, show all ingredients, and patch operations.
 */

@Service
@Transactional

public class IngredientServiceImpl implements IngredientService {

    private final IngredientDao ingredientDao;
    private final IngredientMapper ingredientMapper;
    private final MessageSourceAccessor messages;
    private static final String INGREDIENT_NOTFOUND = "ingredient.not.found";

    public IngredientServiceImpl(IngredientDao ingredientDao, IngredientMapper ingredientMapper, MessageSourceAccessor messages) {
        this.ingredientDao = ingredientDao;
        this.ingredientMapper = ingredientMapper;
        this.messages = messages;
    }

    /**
     * Creates a new ingredient and persists it into the database.
     * @param ingredientRequestDto request containing the ingredient to persist.
     * @return the response dto containing all the ingredient details, including its ID.
     */

    @Override
    public IngredientResponseDto addIngredient(IngredientRequestDto ingredientRequestDto) {

        verifyIngredient(ingredientRequestDto);
        Ingredient savedIngredient = ingredientDao.save(ingredientMapper.toIngredient(ingredientRequestDto));
        return ingredientMapper.toIngredientResponseDto(savedIngredient);

    }

    /**
     * Lists all the ingredients persisted in the database.
     * @return the list of ingredients in the database.
     */
    @Override
    public List<IngredientResponseDto> getAllIngredients() {
        List<Ingredient> savedIngredients = ingredientDao.findAll();
        return savedIngredients.stream()
                .map(ingredientMapper::toIngredientResponseDto)
                .toList();
    }

    /**
     * Find an ingredient in the database by its name.
     * @param name name of the searched ingredient
     * @return all the informations of the ingredient.
     * @throws EntityNotFoundException if the ingredient is not found in the database.
     */
    @Override
    public IngredientResponseDto findIngredientByName(String name) {
        Ingredient ingredient = null;
        try {
            ingredient = ingredientDao.findByIngredientName(name);
        } catch (EntityNotFoundException _) {
            throw new EntityNotFoundException(messages.getMessage(INGREDIENT_NOTFOUND));
        }
        return ingredientMapper.toIngredientResponseDto(ingredient);
    }

    /**
     * Find an ingredient in the database by its id.
     * @param id id of the searched ingredient
     * @return all the informations of the ingredient.
     * @throws EntityNotFoundException if the ingredient is not found in the database.
     */
    @Override
    public IngredientResponseDto findIngredientById(UUID id) {
        Ingredient ingredient = null;
        try {
            ingredient = ingredientDao.getReferenceById(id);
        } catch (EntityNotFoundException _) {
            throw new EntityNotFoundException(messages.getMessage(INGREDIENT_NOTFOUND));
        }
        return ingredientMapper.toIngredientResponseDto(ingredient);
    }

    /**
     * Updates an ingredient stock. The ingredient is found with its name, with the findIngredientByName method.
     * @param name the name of the ingredient.
     * @param stock the new value of the ingredient stock
     * @return the response dto containing all the informations about the updated ingredient.
     * @throws EntityNotFoundException if the ingredient is not found in the database.
     */
    @Override
    public IngredientResponseDto updateIngredientStock(String name, Integer stock) {

        Ingredient ingredient = null;

        try{
            ingredient = ingredientDao.findByIngredientName(name);
        }
        catch (EntityNotFoundException _){
            throw new EntityNotFoundException(messages.getMessage(INGREDIENT_NOTFOUND));
        }

        ingredient.setStock(stock);
        Ingredient savedIngredient = ingredientDao.save(ingredient);

        return ingredientMapper.toIngredientResponseDto(savedIngredient);
    }

    /**
     * Verifies that the ingredient request dto is not null so that the adding of an ingredient can be performed
     * @param ingredientRequestDto the ingredient request dto used to add an ingredient in the database.
     * @throws IngredientException if the request dto is null, with a personalized message.
     */
    @Override
    public void verifyIngredient(IngredientRequestDto ingredientRequestDto) {
        if(ingredientRequestDto == null)
            throw new IngredientException(messages.getMessage("ingredient.null"));
    }

}
