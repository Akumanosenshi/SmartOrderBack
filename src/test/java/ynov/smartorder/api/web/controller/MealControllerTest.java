package ynov.smartorder.api.web.controller;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ynov.smartorder.api.common.utils.WithRandom;
import ynov.smartorder.api.domain.models.Meal;
import ynov.smartorder.api.domain.models.User;
import ynov.smartorder.api.domain.ports.UserPort;
import ynov.smartorder.api.persistence.repository.MealRepository;
import ynov.smartorder.api.web.dtos.*;
import ynov.smartorder.api.web.mappers.MealDtoMapper;
import ynov.smartorder.api.web.mappers.UserDtoMapper;
import ynov.smartorder.api.web.services.BruteForceProtectionService;
import ynov.smartorder.api.web.services.JwtService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class MealControllerTest implements WithRandom {

    @Mock private MealRepository mealRepository;
    @Mock private MealDtoMapper mealMapper;

    @InjectMocks
    private MealController mealController;

    @Test
    void shouldDeleteMeal() {
        // given
        String title = randomString();

        // when
        ResponseEntity<Void> response = mealController.deleteMeal(title);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(mealRepository).deleteMeal(title);
    }

    @Test
    void shouldListMealsWithTitle() {
        // given
        Meal meal = random(Meal.class);
        MealDto mealDto = random(MealDto.class);
        when(mealRepository.getMeal(meal.getTitle())).thenReturn(meal);
        when(mealMapper.toDto(meal)).thenReturn(mealDto);

        // when
        ResponseEntity<List<MealDto>> response = mealController.listMeals(meal.getTitle());

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(mealDto, response.getBody().get(0));
    }

    @Test
    void shouldReturnNoContentIfMealWithTitleNotFound() {
        // given
        String title = randomString();
        when(mealRepository.getMeal(title)).thenReturn(null);

        // when
        ResponseEntity<List<MealDto>> response = mealController.listMeals(title);

        // then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void shouldListAllMealsIfNoTitleProvided() {
        // given
        List<Meal> meals = List.of(random(Meal.class), random(Meal.class));
        List<MealDto> mealDtos = meals.stream().map(meal -> random(MealDto.class)).collect(Collectors.toList());
        when(mealRepository.getAllMeals()).thenReturn(meals);
        for (int i = 0; i < meals.size(); i++) {
            when(mealMapper.toDto(meals.get(i))).thenReturn(mealDtos.get(i));
        }

        // when
        ResponseEntity<List<MealDto>> response = mealController.listMeals(null);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mealDtos, response.getBody());
    }

    @Test
    void shouldReturnNoContentIfNoMealsExist() {
        // given
        when(mealRepository.getAllMeals()).thenReturn(List.of());

        // when
        ResponseEntity<List<MealDto>> response = mealController.listMeals(null);

        // then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void shouldReturnMealCategories() {
        // given
        List<String> categories = List.of("Plat", "Dessert");
        when(mealRepository.GetAllCategories()).thenReturn(categories);

        // when
        ResponseEntity<List<String>> response = mealController.mealCategoriesGet();

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categories, response.getBody());
    }

    @Test
    void shouldReturnNoContentIfNoCategories() {
        // given
        when(mealRepository.GetAllCategories()).thenReturn(List.of());

        // when
        ResponseEntity<List<String>> response = mealController.mealCategoriesGet();

        // then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void shouldReturnSingleMeal() {
        // given
        Meal meal = random(Meal.class);
        MealDto dto = random(MealDto.class);
        when(mealRepository.getMeal(meal.getTitle())).thenReturn(meal);
        when(mealMapper.toDto(meal)).thenReturn(dto);

        // when
        ResponseEntity<MealDto> response = mealController.mealGet(meal.getTitle());

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void shouldReturnNoContentIfMealNotFound() {
        // given
        String title = randomString();
        when(mealRepository.getMeal(title)).thenReturn(null);

        // when
        ResponseEntity<MealDto> response = mealController.mealGet(title);

        // then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void shouldPublishMeals() {
        // given
        List<MealDto> dtos = List.of(random(MealDto.class), random(MealDto.class));

        // when
        ResponseEntity<Void> response = mealController.publishMeal(dtos);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(mealMapper, times(dtos.size())).toEntity(any());
        verify(mealRepository, times(dtos.size())).createMeal(any());
    }

    @Test
    void shouldUpdateMeal() {
        // given
        MealDto dto = random(MealDto.class);
        Meal meal = random(Meal.class);
        when(mealMapper.toEntity(dto)).thenReturn(meal);

        // when
        ResponseEntity<Void> response = mealController.updateMeal(dto);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(mealRepository).updateMeal(meal);
    }
}