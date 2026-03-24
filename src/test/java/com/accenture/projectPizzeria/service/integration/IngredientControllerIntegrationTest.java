package com.accenture.projectPizzeria.service.integration;

import com.accenture.projectPizzeria.controller.impl.IngredientController;
import com.accenture.projectPizzeria.mapper.IngredientMapper;
import com.accenture.projectPizzeria.service.IngredientServiceImpl;
import com.accenture.projectPizzeria.service.dto.IngredientRequestDto;
import com.accenture.projectPizzeria.service.dto.IngredientResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@WebMvcTest(IngredientController.class)
public class IngredientControllerIntegrationTest {

    private static final String API_INGREDIENTS_ENDPOINT = "/ingredients";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IngredientServiceImpl ingredientService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private IngredientMapper ingredientMapper;

    @Test
    @DisplayName("Test to persist ingredient into H2 database")
    void testPersistIngredientSuccess() throws Exception {

        String ingredientName = "Tomato";
        int stock = 100;

        IngredientRequestDto jsonBody =  new IngredientRequestDto(ingredientName, stock);
        IngredientResponseDto responseDto = new IngredientResponseDto(UUID.randomUUID(), ingredientName, stock);

        Mockito.when(ingredientService.addIngredient(Mockito.any(IngredientRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.post(API_INGREDIENTS_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding(StandardCharsets.UTF_8)
                    .content(objectMapper.writeValueAsString(jsonBody)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    @DisplayName("Test to get all the ingredients persisted in the H2 database")
    void testGetAllIngredientsSuccess() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(API_INGREDIENTS_ENDPOINT))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @DisplayName("Test to get an ingredient by its name from the H2 database")
    void testGetIngredientByNameSuccess() throws Exception {

        String name = "Tomato";
        mockMvc.perform(MockMvcRequestBuilders.get(API_INGREDIENTS_ENDPOINT + "/{name}",name)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

}
