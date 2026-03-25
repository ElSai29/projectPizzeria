package com.accenture.projectPizzeria.service;

import com.accenture.projectPizzeria.mapper.PizzaMapper;
import com.accenture.projectPizzeria.repository.PizzaDao;
import com.accenture.projectPizzeria.repository.model.Ingredient;
import com.accenture.projectPizzeria.repository.model.Pizza;
import com.accenture.projectPizzeria.repository.model.PizzaSize;
import com.accenture.projectPizzeria.service.dto.IngredientRequestDto;
import com.accenture.projectPizzeria.service.dto.IngredientResponseDto;
import com.accenture.projectPizzeria.service.dto.PizzaRequestDto;
import com.accenture.projectPizzeria.service.dto.PizzaResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.support.MessageSourceAccessor;
import org.junit.jupiter.api.Assertions.*;

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
        IngredientRequestDto ingredientRequestDto = new IngredientRequestDto(name, stock);
        IngredientResponseDto ingredientResponseDto = new IngredientResponseDto(UUID.randomUUID(), name, stock);
        ingredientResponseDtoList.add(ingredientResponseDto);

        name = "Basil";
        stock = 100;
        Ingredient ingredient2 = new Ingredient(name, stock);
        ingredientEntityList.add(ingredient2);
        ingredientRequestDto = new IngredientRequestDto(name, stock);
        ingredientResponseDto = new IngredientResponseDto(UUID.randomUUID(), name, stock);
        ingredientResponseDtoList.add(ingredientResponseDto);

        Pizza pizzaEntity = new Pizza(pizzaName, ingredientEntityList, pizzaMap);

        PizzaRequestDto pizzaRequestDto = new PizzaRequestDto(pizzaName,  ingredientList, pizzaMap);
        PizzaResponseDto pizzaResponseDto = new PizzaResponseDto(UUID.randomUUID(), pizzaName, ingredientResponseDtoList, pizzaMap);

        Mockito.when(pizzaMapper.toPizza(Mockito.any(PizzaRequestDto.class))).thenReturn(pizzaEntity);
        Mockito.when(pizzaDao.save(Mockito.any(Pizza.class))).thenReturn(pizzaEntity);
        Mockito.when(pizzaMapper.toPizzaResponseDto(Mockito.any(Pizza.class))).thenReturn(pizzaResponseDto);

        PizzaResponseDto returnedValue = spy.addPizza(pizzaRequestDto);



        Mockito.verify(spy, Mockito.times(1)).verifyPizza(Mockito.any(PizzaRequestDto.class));

    }

}
