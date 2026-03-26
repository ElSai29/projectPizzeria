package com.accenture.projectPizzeria.service;

import com.accenture.projectPizzeria.exception.PizzaException;
import com.accenture.projectPizzeria.mapper.PizzaMapper;
import com.accenture.projectPizzeria.repository.PizzaDao;
import com.accenture.projectPizzeria.repository.model.Pizza;
import com.accenture.projectPizzeria.service.dto.PizzaRequestDto;
import com.accenture.projectPizzeria.service.dto.PizzaResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service handling bsuiness logic related to pizzas.
 * This service is responsible for managing ingredients, including creation operation.
 */

@Service
@AllArgsConstructor
@Transactional

public class PizzaServiceImpl implements PizzaService{

    private final PizzaDao pizzaDao;
    private final PizzaMapper pizzaMapper;
    private final IngredientService ingredientService;
    private final MessageSourceAccessor messages;

    /**
     * Creates a new pizza and persists it into the database.
     * @param pizzaRequestDto request containing the ingredient to persist.
     * @return the response dto containing all the pizza details, including its ID.
     */
    @Override
    public PizzaResponseDto addPizza(PizzaRequestDto pizzaRequestDto) {

        verifyPizza(pizzaRequestDto);
        Pizza savedPizza = pizzaDao.save(pizzaMapper.toPizza(pizzaRequestDto));
        return pizzaMapper.toPizzaResponseDto(savedPizza);

    }

    /**
     * Verifies that the pizza request dto is not null so that the adding of a pizza can be performed
     * @param pizzaRequestDto the pizza request dto used to add a pizza in the database.
     * @throws PizzaException if the request dto is null, with a personalized message.
     */
    @Override
    public void verifyPizza(PizzaRequestDto pizzaRequestDto) {
        if(pizzaRequestDto == null){
            throw new PizzaException(messages.getMessage("pizza.null"));
        }
    }
}
