package com.accenture.projectPizzeria.service;

import com.accenture.projectPizzeria.service.dto.PizzaRequestDto;
import com.accenture.projectPizzeria.service.dto.PizzaResponseDto;

public interface PizzaService {

    PizzaResponseDto addPizza(PizzaRequestDto pizzaRequestDto);

    void verifyPizza(PizzaRequestDto pizzaRequestDto);

}
