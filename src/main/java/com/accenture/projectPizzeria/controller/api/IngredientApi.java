package com.accenture.projectPizzeria.controller.api;

import com.accenture.projectPizzeria.controller.advice.ErrorDto;
import com.accenture.projectPizzeria.service.dto.IngredientRequestDto;
import com.accenture.projectPizzeria.service.dto.IngredientResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "Ingredients", description = "Ingredient managing API")
@RequestMapping("/ingredients")

public interface IngredientApi {

    @Operation(summary = "Add a new ingredient")
    @ApiResponse(responseCode = "200", description = "Ingredient found")
    @ApiResponse(responseCode = "400", description = "Invalid ingredient",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))

    @PostMapping
    ResponseEntity<Void> addIngredient(@RequestBody IngredientRequestDto requestDto);

    @Operation(summary = "Read all ingredients")
    @ApiResponse(responseCode = "200", description = "List all ingredients")
    @GetMapping
    ResponseEntity<List<IngredientResponseDto>> getAllIngredients();

}
