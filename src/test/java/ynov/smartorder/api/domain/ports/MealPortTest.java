package ynov.smartorder.api.domain.ports;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ynov.smartorder.api.common.utils.WithRandom;
import ynov.smartorder.api.domain.models.Meal;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MealPortTest implements WithRandom {

    @Mock
    private MealPort mealPort;

    @Test
    void shouldCreateMeal() {
        // given
        Meal meal = random(Meal.class);

        // when
        mealPort.createMeal(meal);

        // then
        verify(mealPort).createMeal(meal);
    }

    @Test
    void shouldUpdateMeal() {
        // given
        Meal meal = random(Meal.class);

        // when
        mealPort.updateMeal(meal);

        // then
        verify(mealPort).updateMeal(meal);
    }

    @Test
    void shouldDeleteMealByName() {
        // given
        String title = randomString();

        // when
        mealPort.deleteMeal(title);

        // then
        verify(mealPort).deleteMeal(title);
    }

    @Test
    void shouldReturnMealByName() {
        // given
        String title = randomString();
        Meal expectedMeal = random(Meal.class);
        when(mealPort.getMeal(title)).thenReturn(expectedMeal);

        // when
        Meal result = mealPort.getMeal(title);

        // then
        assertEquals(expectedMeal, result);
    }

    @Test
    void shouldReturnAllMeals() {
        // given
        List<Meal> meals = List.of(random(Meal.class), random(Meal.class));
        when(mealPort.getAllMeals()).thenReturn(meals);

        // when
        List<Meal> result = mealPort.getAllMeals();

        // then
        assertEquals(meals, result);
    }

    @Test
    void shouldReturnAllCategories() {
        // given
        List<String> categories = List.of("Entrée", "Plat", "Dessert");
        when(mealPort.GetAllCategories()).thenReturn(categories);

        // when
        List<String> result = mealPort.GetAllCategories();

        // then
        assertEquals(categories, result);
    }
}

