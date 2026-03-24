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

}
