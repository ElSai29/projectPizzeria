package com.accenture.projectPizzeria.controller.api;

import com.accenture.projectPizzeria.controller.advice.ErrorDto;
import com.accenture.projectPizzeria.service.dto.IngredientRequestDto;
import com.accenture.projectPizzeria.service.dto.PizzaRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Pizzas", description = "Pizza managing API")
@RequestMapping("/pizzas")

public interface PizzaApi {

    @Operation(summary = "Add a new pizza")
    @ApiResponse(responseCode = "201", description = "Pizza created")
    @ApiResponse(responseCode = "400", description = "Invalid pizza",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @PostMapping
    ResponseEntity<Void> addPizza(@RequestBody PizzaRequestDto requestDto);

}
