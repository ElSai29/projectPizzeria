package com.accenture.projectPizzeria.controller.pizza;

import com.accenture.projectPizzeria.mapper.IngredientMapper;
import com.accenture.projectPizzeria.repository.model.PizzaSize;
import com.accenture.projectPizzeria.service.PizzaServiceImpl;
import com.accenture.projectPizzeria.service.dto.PizzaRequestDto;
import com.accenture.projectPizzeria.service.dto.PizzaResponseDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class PizzaControllerIntegrationEndToEndTest {

    private static final String API_PIZZAS_ENDPOINTS = "/pizzas";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PizzaServiceImpl pizzaService;

    @Autowired
    private IngredientMapper ingredientMapper;

    @BeforeEach
    @Sql(statements = {"DELETE FROM PIZZAS"})
    public void setUp() {}

    @Test
    @DisplayName("Creates a pizza through Post endpoint")
    @Order(1)
    @Sql("/scripts/ingredients_injection.sql")
    public void testPostPizzaSuccess() throws Exception {

        String pizzaName = "Margherita";
        List<String> ingredientList =  new ArrayList<>();
        ingredientList.add("Tomato");
        ingredientList.add("Basil");
        HashMap<PizzaSize, Double> pizzaMap = new HashMap<>();
        pizzaMap.put(PizzaSize.SMALL, 10.0);
        pizzaMap.put(PizzaSize.MEDIUM, 15.0);
        pizzaMap.put(PizzaSize.LARGE, 20.0);

        PizzaRequestDto pizzaRequestDto = new PizzaRequestDto(pizzaName, ingredientList, pizzaMap);

        ResponseEntity<Void> response = restTemplate.postForEntity("http://localhost:" + port + API_PIZZAS_ENDPOINTS, pizzaRequestDto, Void.class);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Post pizza must return a 201 http response code.");

    }

}
