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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

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

    @BeforeEach
    @Sql(statements = {"DELETE FROM INGREDIENTS"})
    public void setUp() {}

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

    @Test
    @Order(2)
    @DisplayName("Test the find all through Get endpoint")
    @Sql("/scripts/ingredients_injection.sql")
    void testGetAllIngredientsSuccess() {

        ResponseEntity<List<IngredientResponseDto>> responseIngredients = restTemplate.exchange("http://localhost:" + port + API_INGREDIENTS_ENDPOINTS, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        List<IngredientResponseDto> ingredients = responseIngredients.getBody();

        Assertions.assertAll(() -> {
            Assertions.assertEquals(HttpStatus.OK, responseIngredients.getStatusCode());
            Assertions.assertNotNull(responseIngredients.getBody());
            Assertions.assertEquals("Tomato", ingredients.getFirst().ingredientName());
            Assertions.assertEquals(100, ingredients.getFirst().stock());
        });

    }

    @Test
    @Order(3)
    @DisplayName("Test the find by name through Get endpoint")
    @Sql("/scripts/ingredients_injection.sql")
    void testGetIngredientByNameSuccess() {

        String name = "Tomato";

        String url = UriComponentsBuilder
                .fromUriString("http://localhost:" + port + API_INGREDIENTS_ENDPOINTS + "/{name}")
                .buildAndExpand(name)
                .toUriString();

        ResponseEntity<IngredientResponseDto> responseIngredients = restTemplate.exchange(url, HttpMethod.GET, null, IngredientResponseDto.class);

        Assertions.assertAll(() -> {
            Assertions.assertEquals(HttpStatus.OK, responseIngredients.getStatusCode());
            Assertions.assertNotNull(responseIngredients.getBody());
            Assertions.assertEquals("Tomato", responseIngredients.getBody().ingredientName());
        });

    }

    @Test
    @Order(4)
    @DisplayName("Test the find by id through Get endpoint")
    @Sql("/scripts/ingredients_injection.sql")
    void testGetIngredientByIdSuccess() {

        UUID id = UUID.fromString("996e267f-8512-4089-b539-e75729d984b0");

        String url = UriComponentsBuilder
                .fromUriString("http://localhost:" + port + API_INGREDIENTS_ENDPOINTS + "/findById/{id}")
                .buildAndExpand(id)
                .toUriString();

        ResponseEntity<IngredientResponseDto> responseIngredients = restTemplate.exchange(url, HttpMethod.GET, null, IngredientResponseDto.class);

        Assertions.assertAll(() -> {
            Assertions.assertEquals(HttpStatus.OK, responseIngredients.getStatusCode());
            Assertions.assertNotNull(responseIngredients.getBody());
            Assertions.assertEquals("996e267f-8512-4089-b539-e75729d984b0", responseIngredients.getBody().id().toString());
        });

    }

    @Test
    @Order(5)
    @DisplayName("Test the patch of an ingredient through Patch endpoint")
    @Sql("/scripts/ingredients_injection.sql")
    void testPatchIngredientSuccess() {

        UUID id = UUID.fromString("996e267f-8512-4089-b539-e75729d984b0");
        Integer newStock = 50;

        String url = UriComponentsBuilder
                .fromUriString("http://localhost:" + port + API_INGREDIENTS_ENDPOINTS + "/patch/{id}")
                .buildAndExpand(id)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Integer> request = new HttpEntity<>(newStock, headers);
        ResponseEntity<IngredientResponseDto> response = restTemplate.exchange(url, HttpMethod.PATCH, request, IngredientResponseDto.class);

        Assertions.assertAll(() -> {
            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
            Assertions.assertNotNull(response.getBody());
            Assertions.assertEquals(50, response.getBody().stock());
        });

    }

}