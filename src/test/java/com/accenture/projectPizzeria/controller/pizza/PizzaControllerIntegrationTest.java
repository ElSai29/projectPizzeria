package com.accenture.projectPizzeria.controller.pizza;

import com.accenture.projectPizzeria.controller.impl.PizzaController;
import com.accenture.projectPizzeria.mapper.IngredientMapper;
import com.accenture.projectPizzeria.mapper.PizzaMapper;
import com.accenture.projectPizzeria.repository.model.PizzaSize;
import com.accenture.projectPizzeria.service.PizzaServiceImpl;
import com.accenture.projectPizzeria.service.dto.IngredientResponseDto;
import com.accenture.projectPizzeria.service.dto.PizzaRequestDto;
import com.accenture.projectPizzeria.service.dto.PizzaResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@WebMvcTest(PizzaController.class)
class PizzaControllerIntegrationTest {

    private static final String API_PIZZAS_ENDPOINT = "/pizzas";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PizzaServiceImpl pizzaService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PizzaMapper pizzaMapper;

    @MockitoBean
    private IngredientMapper ingredientMapper;

    @Test
    @DisplayName("Test to persist a pizza in the H2 database")
    void testPersistPizzaSuccess() throws Exception {

        String pizzaName = "Margherita";
        List<String> ingredientList =  new ArrayList<>();
        ingredientList.add("Tomato");
        ingredientList.add("Basil");
        HashMap<PizzaSize, Double> pizzaMap = new HashMap<>();
        pizzaMap.put(PizzaSize.SMALL, 10.0);
        pizzaMap.put(PizzaSize.MEDIUM, 15.0);
        pizzaMap.put(PizzaSize.LARGE, 20.0);

        List<IngredientResponseDto> ingredientResponseDtoList = new ArrayList<>();
        String name = "Tomato";

        String name2 = "Basil";

        int stock = 100;
        IngredientResponseDto ingredientResponseDto = new IngredientResponseDto(UUID.randomUUID(), name, stock);
        ingredientResponseDtoList.add(ingredientResponseDto);

        ingredientResponseDto = new IngredientResponseDto(UUID.randomUUID(), name2, stock);
        ingredientResponseDtoList.add(ingredientResponseDto);

        PizzaRequestDto jsonBody = new PizzaRequestDto(pizzaName, ingredientList, pizzaMap);
        PizzaResponseDto responseDto = new PizzaResponseDto(UUID.randomUUID(), pizzaName, ingredientResponseDtoList, pizzaMap);

        Mockito.when(pizzaService.addPizza(Mockito.any(PizzaRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.post(API_PIZZAS_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding(StandardCharsets.UTF_8)
                    .content(objectMapper.writeValueAsString(jsonBody)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }

}
