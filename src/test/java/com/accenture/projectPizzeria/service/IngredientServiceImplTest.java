package com.accenture.projectPizzeria.service;

import com.accenture.projectPizzeria.mapper.IngredientMapper;
import com.accenture.projectPizzeria.repository.IngredientDao;
import com.accenture.projectPizzeria.repository.model.Ingredient;
import com.accenture.projectPizzeria.service.dto.IngredientRequestDto;
import com.accenture.projectPizzeria.service.dto.IngredientResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.support.MessageSourceAccessor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class IngredientServiceImplTest {

    @Mock
    private IngredientDao ingredientDao;

    @Mock
    private IngredientMapper ingredientMapper;

    @Mock
    private MessageSourceAccessor messages;

    private IngredientService ingredientService;

    @BeforeEach
    public void setUp() {

        ingredientDao = mock(IngredientDao.class);
        ingredientMapper = mock(IngredientMapper.class);
        messages = mock(MessageSourceAccessor.class);
        ingredientService = new IngredientServiceImpl(ingredientDao, ingredientMapper, messages);

    }

    @Test
    @DisplayName("Test when ingredient is well persisted from valid input")
    void addIngredientValidInputOk() {

        IngredientService spy = Mockito.spy(ingredientService);
        String ingredientName = "Tomato";
        int stock = 100;

        IngredientRequestDto requestDto =  new IngredientRequestDto(ingredientName, stock);
        IngredientResponseDto responseDto = new IngredientResponseDto(UUID.randomUUID(), ingredientName, stock);
        Ingredient ingredientEntity = new Ingredient(ingredientName, stock);

        Mockito.when(ingredientMapper.toIngredient(Mockito.any(IngredientRequestDto.class))).thenReturn(ingredientEntity);
        Mockito.when(ingredientDao.save(Mockito.any(Ingredient.class))).thenReturn(ingredientEntity);
        Mockito.when(ingredientMapper.toIngredientResponseDto(Mockito.any(Ingredient.class))).thenReturn(responseDto);

        IngredientResponseDto returnedIngredient = spy.addIngredient(requestDto);

        Mockito.verify(spy, Mockito.times(1)).verifyIngredient(Mockito.any(IngredientRequestDto.class));

        Assertions.assertAll(() -> Assertions.assertNotNull(returnedIngredient,"IngredientResponseDto should not be null"),
                () -> Assertions.assertNotNull(returnedIngredient.id(), "Ingredient ID should not be null"),
                () -> Assertions.assertNotNull(returnedIngredient.ingredientName(), "Ingredient name should not be null"),
                () -> Assertions.assertNotNull(returnedIngredient.stock(), "Ingredient stock should not be null"),
                () -> Assertions.assertEquals(ingredientName, returnedIngredient.ingredientName(), "Ingredient name should be the same as expected"),
                () -> Assertions.assertEquals(stock, returnedIngredient.stock(), "Ingredient stock should be the same as expected"));

    }

    @Test
    @DisplayName("Test getting all the ingredients persisted in the H2 database")
    void getAllIngredientsOk() {

        IngredientService spy = Mockito.spy(ingredientService);

        UUID idTomato = UUID.randomUUID();
        String ingredientNameTomato = "Tomato";
        int stockTomato = 100;

        IngredientResponseDto responseDtoTomato = new IngredientResponseDto(idTomato, ingredientNameTomato, stockTomato);
        Ingredient ingredientEntityTomato = new Ingredient(ingredientNameTomato, stockTomato);

        UUID idBasil = UUID.randomUUID();
        String ingredientNameBasil = "Basil";
        int stockBasil = 100;

        IngredientResponseDto responseDtoBasil = new IngredientResponseDto(idBasil, ingredientNameBasil, stockBasil);
        Ingredient ingredientEntityBasil = new Ingredient(ingredientNameBasil, stockBasil);

        List<Ingredient> listIngredients =  new ArrayList<>();
        listIngredients.add(ingredientEntityTomato);
        listIngredients.add(ingredientEntityBasil);

        Mockito.when(ingredientDao.findAll()).thenReturn(listIngredients);
        Mockito.when(ingredientMapper.toIngredientResponseDto(ingredientEntityTomato)).thenReturn(responseDtoTomato);
        Mockito.when(ingredientMapper.toIngredientResponseDto(ingredientEntityBasil)).thenReturn(responseDtoBasil);

        List<IngredientResponseDto> returnedIngredients = spy.getAllIngredients();

        Assertions.assertAll(
                () -> Assertions.assertEquals(idTomato, returnedIngredients.getFirst().id(), "Tomato id should match the expected."),
                () -> Assertions.assertEquals(idBasil, returnedIngredients.getLast().id(), "Basil id should match the expected."),
                () -> Assertions.assertEquals(ingredientNameTomato, returnedIngredients.getFirst().ingredientName(), "Tomato name should match the expected."),
                () -> Assertions.assertEquals(ingredientNameBasil, returnedIngredients.getLast().ingredientName(), "Basil name should match the expected."),
                () -> Assertions.assertEquals(stockTomato, returnedIngredients.getFirst().stock(), "Tomato stock should match the expected."),
                () -> Assertions.assertEquals(stockBasil, returnedIngredients.getLast().stock(), "Basil stock should match the expected.")
        );

    }

}
