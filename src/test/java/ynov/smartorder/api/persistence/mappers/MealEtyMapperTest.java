package ynov.smartorder.api.persistence.mappers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import ynov.smartorder.api.common.utils.WithRandom;
import ynov.smartorder.api.domain.models.Meal;
import ynov.smartorder.api.persistence.entities.MealEty;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MealEtyMapperTest implements WithRandom {

    private final MealEtyMapper mapper = Mappers.getMapper(MealEtyMapper.class);

    @Test
    void shouldMapMealToMealEty() {
        // given
        Meal meal = random(Meal.class);

        // when
        MealEty result = mapper.toEty(meal);

        // then
        assertNotNull(result);
        assertEquals(meal.getTitle(), result.getTitle());
        assertEquals(meal.getCategory(), result.getCategory());
        assertEquals(meal.getDescription(), result.getDescription());
        assertEquals(meal.getEmoji(), result.getEmoji());
        assertEquals(meal.getPrice(), result.getPrice());
    }

    @Test
    void shouldMapMealEtyToMeal() {
        // given
        MealEty mealEty = random(MealEty.class);

        // when
        Meal result = mapper.toModel(mealEty);

        // then
        assertNotNull(result);
        assertEquals(mealEty.getTitle(), result.getTitle());
        assertEquals(mealEty.getCategory(), result.getCategory());
        assertEquals(mealEty.getDescription(), result.getDescription());
        assertEquals(mealEty.getEmoji(), result.getEmoji());
        assertEquals(mealEty.getPrice(), result.getPrice());
    }
}


