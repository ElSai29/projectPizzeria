package com.accenture.projectPizzeria.service;

import com.accenture.projectPizzeria.mapper.PizzaMapper;
import com.accenture.projectPizzeria.repository.PizzaDao;
import com.accenture.projectPizzeria.repository.model.Ingredient;
import com.accenture.projectPizzeria.repository.model.Pizza;
import com.accenture.projectPizzeria.repository.model.PizzaSize;
import com.accenture.projectPizzeria.service.dto.IngredientResponseDto;
import com.accenture.projectPizzeria.service.dto.PizzaRequestDto;
import com.accenture.projectPizzeria.service.dto.PizzaResponseDto;
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
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class PizzaServiceImplTest {

    @Mock
    private PizzaDao pizzaDao;

    @Mock
    private MessageSourceAccessor messages;

    @Mock
    private PizzaMapper pizzaMapper;

    @Mock
    private IngredientService ingredientService;

    private PizzaService pizzaService;

    @BeforeEach
    void setUp() {

        pizzaDao = Mockito.mock(PizzaDao.class);
        messages = Mockito.mock(MessageSourceAccessor.class);
        pizzaMapper = Mockito.mock(PizzaMapper.class);
        ingredientService = Mockito.mock(IngredientService.class);
        pizzaService = new PizzaServiceImpl(pizzaDao, pizzaMapper, ingredientService, messages);

    }

    @Test
    @DisplayName("Add pizza")
    void addPizzaSuccess() {

        PizzaService spy =  Mockito.spy(pizzaService);

        String pizzaName = "Margherita";
        List<String> ingredientList =  new ArrayList<>();
        ingredientList.add("Tomato");
        ingredientList.add("Basil");
        HashMap<PizzaSize, Double> pizzaMap = new HashMap<>();
        pizzaMap.put(PizzaSize.SMALL, 10.0);
        pizzaMap.put(PizzaSize.MEDIUM, 15.0);
        pizzaMap.put(PizzaSize.LARGE, 20.0);

        List<IngredientResponseDto> ingredientResponseDtoList = new ArrayList<>();
        List<Ingredient> ingredientEntityList = new ArrayList<>();
        String name = "Tomato";
        int stock = 100;
        Ingredient ingredient = new Ingredient(name, stock);
        ingredientEntityList.add(ingredient);
        IngredientResponseDto ingredientResponseDto = new IngredientResponseDto(UUID.randomUUID(), name, stock);
        ingredientResponseDtoList.add(ingredientResponseDto);

        String name2 = "Basil";
        Ingredient ingredient2 = new Ingredient(name2, stock);
        ingredientEntityList.add(ingredient2);
        ingredientResponseDto = new IngredientResponseDto(UUID.randomUUID(), name2, stock);
        ingredientResponseDtoList.add(ingredientResponseDto);

        Pizza pizzaEntity = new Pizza(pizzaName, ingredientEntityList, pizzaMap);

        PizzaRequestDto pizzaRequestDto = new PizzaRequestDto(pizzaName,  ingredientList, pizzaMap);
        PizzaResponseDto pizzaResponseDto = new PizzaResponseDto(UUID.randomUUID(), pizzaName, ingredientResponseDtoList, pizzaMap);

        Mockito.when(pizzaMapper.toPizza(Mockito.any(PizzaRequestDto.class))).thenReturn(pizzaEntity);
        Mockito.when(pizzaDao.save(Mockito.any(Pizza.class))).thenReturn(pizzaEntity);
        Mockito.when(pizzaMapper.toPizzaResponseDto(Mockito.any(Pizza.class))).thenReturn(pizzaResponseDto);

        PizzaResponseDto returnedValue = spy.addPizza(pizzaRequestDto);

        Assertions.assertAll(
                () -> Assertions.assertNotNull(returnedValue, "DtoResponse should not be null"),
                () -> Assertions.assertNotNull(returnedValue.id(), "Id should not be null"),
                () -> Assertions.assertNotNull(returnedValue.name(), "Name should not be null"),
                () -> Assertions.assertNotNull(returnedValue.ingredientList(), "Ingredient list should not be null"),
                () -> Assertions.assertNotNull(returnedValue.prices(), "Sizes and prices should not be null"),
                () -> Assertions.assertEquals(pizzaName, returnedValue.name(), "Pizza name should be the same as expected"),
                () -> Assertions.assertEquals(name, returnedValue.ingredientList().getFirst().ingredientName(), "First ingredient name should be the same as expected"),
                () -> Assertions.assertEquals(name2, returnedValue.ingredientList().getLast().ingredientName(), "Last ingredient name should be the same as expected"),
                () -> Assertions.assertEquals(stock, returnedValue.ingredientList().getFirst().stock(), "First ingredient stock should be the same as expected"),
                () -> Assertions.assertEquals(stock, returnedValue.ingredientList().getLast().stock(), "Last ingredient stock should be the same as expected"),
                () -> Assertions.assertEquals(pizzaMap, returnedValue.prices(), "Sizes and prices should be the same as expected")
        );

        Mockito.verify(spy, Mockito.times(1)).verifyPizza(Mockito.any(PizzaRequestDto.class));

    }

}
