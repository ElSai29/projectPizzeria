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

@Service
@AllArgsConstructor
@Transactional

public class PizzaServiceImpl implements PizzaService{

    private final PizzaDao pizzaDao;
    private final PizzaMapper pizzaMapper;
    private final IngredientService ingredientService;
    private final MessageSourceAccessor messages;


    @Override
    public PizzaResponseDto addPizza(PizzaRequestDto pizzaRequestDto) {

        verifyPizza(pizzaRequestDto);
        Pizza savedPizza = pizzaDao.save(pizzaMapper.toPizza(pizzaRequestDto));
        return pizzaMapper.toPizzaResponseDto(savedPizza);

    }

    @Override
    public void verifyPizza(PizzaRequestDto pizzaRequestDto) {
        if(pizzaRequestDto == null){
            throw new PizzaException(messages.getMessage("pizza.null"));
        }
    }
}
