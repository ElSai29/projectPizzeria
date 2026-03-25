package com.accenture.projectPizzeria.controller.impl;

import com.accenture.projectPizzeria.controller.api.IngredientApi;
import com.accenture.projectPizzeria.service.IngredientService;
import com.accenture.projectPizzeria.service.dto.IngredientRequestDto;
import com.accenture.projectPizzeria.service.dto.IngredientResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<Void> addIngredient(@Valid @RequestBody IngredientRequestDto requestDto) {

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
    public ResponseEntity<IngredientResponseDto> getIngredient(@Valid String ingredientName) {

        log.info("Accessing endpoint GET /ingredients/{name}");
        IngredientResponseDto dto = ingredientService.findIngredientByName(ingredientName);

        if(dto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(ingredientService.findIngredientByName(ingredientName));
    }

    @Override
    public ResponseEntity<IngredientResponseDto> getIngredientById(UUID id) {
        log.info("Accessing endpoint GET /ingredients/findById/{name}");

        IngredientResponseDto dto = ingredientService.findIngredientById(id);

        if(dto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(ingredientService.findIngredientById(id));
    }

    @Override
    public ResponseEntity<IngredientResponseDto> updateIngredientStock(String name, @Valid @RequestBody Integer stock) {

        log.info("Accessing endpoint PATCH /ingredients/patch/{name}");
        IngredientResponseDto ingredientResponseDto = ingredientService.updateIngredientStock(name, stock);
        return ResponseEntity.ok(ingredientResponseDto);
    }
}
