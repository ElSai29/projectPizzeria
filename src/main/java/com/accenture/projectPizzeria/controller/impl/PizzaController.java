package com.accenture.projectPizzeria.controller.impl;

import com.accenture.projectPizzeria.controller.api.PizzaApi;
import com.accenture.projectPizzeria.service.PizzaService;
import com.accenture.projectPizzeria.service.dto.IngredientResponseDto;
import com.accenture.projectPizzeria.service.dto.PizzaRequestDto;
import com.accenture.projectPizzeria.service.dto.PizzaResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@AllArgsConstructor
@Slf4j

public class PizzaController implements PizzaApi {

    private final PizzaService pizzaService;

    @Override
    public ResponseEntity<Void> addPizza(@Valid @RequestBody PizzaRequestDto requestDto) {

        log.info("Accessing endpoint POST /pizzas");
        PizzaResponseDto pizzaResponseDto = pizzaService.addPizza(requestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pizzaResponseDto.id())
                .toUri();
        return ResponseEntity.created(location).build();

    }

}
