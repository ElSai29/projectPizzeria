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
import java.util.List;
import java.util.UUID;

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

    @Override
    public ResponseEntity<List<IngredientResponseDto>> getAllIngredients() {

        log.info("Accessing endpoint GET /ingredients");

        return ResponseEntity.ok(ingredientService.getAllIngredients());
    }

    @Override
    public ResponseEntity<IngredientResponseDto> getIngredient(String ingredientName) {
        return ResponseEntity.ok(ingredientService.findIngredientByName(ingredientName));
    }

    @Override
    public ResponseEntity<IngredientResponseDto> getIngredientById(UUID id) {
        return ResponseEntity.ok(ingredientService.findIngredientById(id));
    }

    @Override
    public ResponseEntity<IngredientResponseDto> updateIngredientStock(UUID id, Integer stock) {
        IngredientResponseDto ingredientResponseDto = ingredientService.updateIngredientStock(id, stock);
        return ResponseEntity.ok(ingredientResponseDto);
    }
}
