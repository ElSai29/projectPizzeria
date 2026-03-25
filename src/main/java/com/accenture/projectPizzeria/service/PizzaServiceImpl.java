package com.accenture.projectPizzeria.service;

import com.accenture.projectPizzeria.mapper.PizzaMapper;
import com.accenture.projectPizzeria.repository.PizzaDao;
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
        return null;
    }

    @Override
    public void verifyPizza(PizzaRequestDto pizzaRequestDto) {

    }
}
