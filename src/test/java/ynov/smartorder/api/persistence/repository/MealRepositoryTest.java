package ynov.smartorder.api.persistence.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ynov.smartorder.api.common.utils.WithRandom;
import ynov.smartorder.api.domain.models.Meal;
import ynov.smartorder.api.persistence.entities.MealEty;
import ynov.smartorder.api.persistence.mappers.MealEtyMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MealRepositoryTest implements WithRandom {

    @Mock
    private MealRepositoryJPA mealRepositoryJPA;

    @Mock
    private MealEtyMapper mealEtyMapper;

    @InjectMocks
    private MealRepository mealRepository;

    @Test
    void shouldCreateMeal() {
        // given
        Meal meal = random(Meal.class);
        MealEty mealEty = random(MealEty.class);
        when(mealEtyMapper.toEty(meal)).thenReturn(mealEty);

        // when
        mealRepository.createMeal(meal);

        // then
        verify(mealRepositoryJPA).save(mealEty);
    }

    @Test
    void shouldUpdateMealIfExists() {
        // given
        Meal meal = random(Meal.class);
        meal.setTitle("Pizza");

        MealEty existing = new MealEty();
        when(mealRepositoryJPA.findByTitle("Pizza")).thenReturn(Optional.of(existing));

        // when
        mealRepository.updateMeal(meal);

        // then
        verify(mealRepositoryJPA).save(existing);
        assertEquals(meal.getCategory(), existing.getCategory());
        assertEquals(meal.getDescription(), existing.getDescription());
        assertEquals(meal.getEmoji(), existing.getEmoji());
        assertEquals(meal.getPrice(), existing.getPrice());
    }

    @Test
    void shouldDeleteMealIfExists() {
        // given
        MealEty mealEty = random(MealEty.class);
        String title = "Burger";
        when(mealRepositoryJPA.findByTitle(title)).thenReturn(Optional.of(mealEty));

        // when
        mealRepository.deleteMeal(title);

        // then
        verify(mealRepositoryJPA).delete(mealEty);
    }

    @Test
    void shouldGetMealByTitle() {
        // given
        String title = "Tacos";
        MealEty mealEty = random(MealEty.class);
        Meal meal = random(Meal.class);
        when(mealRepositoryJPA.findByTitle(title)).thenReturn(Optional.of(mealEty));
        when(mealEtyMapper.toModel(mealEty)).thenReturn(meal);

        // when
        Meal result = mealRepository.getMeal(title);

        // then
        assertEquals(meal, result);
    }

    @Test
    void shouldGetAllMeals() {
        // given
        List<MealEty> entities = List.of(random(MealEty.class), random(MealEty.class));
        List<Meal> models = entities.stream()
                .map(e -> random(Meal.class))
                .collect(Collectors.toList());
        when(mealRepositoryJPA.findAll()).thenReturn(entities);
        for (int i = 0; i < entities.size(); i++) {
            when(mealEtyMapper.toModel(entities.get(i))).thenReturn(models.get(i));
        }

        // when
        List<Meal> result = mealRepository.getAllMeals();

        // then
        assertEquals(models, result);
    }

    @Test
    void shouldGetAllCategories() {
        // given
        List<String> categories = List.of("Entrée", "Plat", "Dessert");
        when(mealRepositoryJPA.findDistinctCategories()).thenReturn(categories);

        // when
        List<String> result = mealRepository.GetAllCategories();

        // then
        assertEquals(categories, result);
    }
}

