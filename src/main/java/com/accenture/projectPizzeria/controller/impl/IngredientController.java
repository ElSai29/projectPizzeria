package com.accenture.projectPizzeria.controller.impl;

import com.accenture.projectPizzeria.controller.api.IngredientApi;
import com.accenture.projectPizzeria.service.IngredientService;
import com.accenture.projectPizzeria.service.dto.IngredientRequestDto;
import com.accenture.projectPizzeria.service.dto.IngredientResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@AllArgsConstructor
@Slf4j
public class IngredientController implements IngredientApi {

    private final IngredientService ingredientService;

    @Override
    public ResponseEntity<Void> addIngredient(IngredientRequestDto requestDto) {

        log.info("Accessing endpoint POST /ingredients/{id}");

        IngredientResponseDto ingredientResponseDto = ingredientService.addIngredient(requestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(ingredientResponseDto.id())
                .toUri();
        return ResponseEntity.created(location).build();

    }
}
