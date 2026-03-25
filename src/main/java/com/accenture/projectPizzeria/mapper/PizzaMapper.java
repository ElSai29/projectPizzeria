package com.accenture.projectPizzeria.mapper;

import com.accenture.projectPizzeria.repository.model.Pizza;
import com.accenture.projectPizzeria.service.dto.PizzaRequestDto;
import com.accenture.projectPizzeria.service.dto.PizzaResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PizzaMapper {

    Pizza toPizza(PizzaRequestDto pizzaRequestDto);
    PizzaResponseDto toPizzaResponseDto(Pizza pizza);

}
