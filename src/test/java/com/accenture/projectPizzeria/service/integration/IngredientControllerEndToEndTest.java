package com.accenture.projectPizzeria.service.integration;

import com.accenture.projectPizzeria.mapper.IngredientMapper;
import com.accenture.projectPizzeria.service.IngredientServiceImpl;
import com.accenture.projectPizzeria.service.dto.IngredientRequestDto;
import com.accenture.projectPizzeria.service.dto.IngredientResponseDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class IngredientControllerEndToEndTest {

    private static final String API_INGREDIENTS_ENDPOINTS = "/ingredients";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private IngredientServiceImpl ingredientService;

    @Test
    @Order(1)
    @DisplayName("Creates an ingredient through Post endpoint.")
    void testPostIngredientSuccess() {

        String ingredientName = "Tomato";
        int stock = 100;

        IngredientRequestDto  ingredientRequestDto = new IngredientRequestDto(ingredientName, stock);
        IngredientResponseDto ingredientResponseDto = ingredientService.addIngredient(ingredientRequestDto);

        ResponseEntity<Void> response = restTemplate.postForEntity("http://localhost:" + port + API_INGREDIENTS_ENDPOINTS, ingredientResponseDto, Void.class);

        Assertions.assertAll(() -> {
            Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Post ingredient must return a 201 http response code.");
            Assertions.assertNotNull(ingredientResponseDto, "Ingredient dto response returned from service must not be null.");
            Assertions.assertNotNull(ingredientResponseDto.id(), "Ingredient return must not have a null UUID.");
            Assertions.assertEquals(ingredientName, ingredientResponseDto.ingredientName(), "Ingredient name must match the request ingredient name.");
            Assertions.assertEquals(stock, ingredientResponseDto.stock(), "Ingredient stock must match the request ingredient stock.");
        });

    }

}
