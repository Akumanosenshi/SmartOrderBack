package ynov.smartorder.api.domain.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ynov.smartorder.api.common.utils.WithRandom;
import java.math.BigDecimal;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class MealTest implements WithRandom {

    @Test
    void shouldCreateMealWithCorrectValues() {
        // given
        UUID id = randomUUID();
        String title = randomString();
        String category = randomString();
        String description = randomString();
        String emoji = randomString();
        BigDecimal price = randomBigDecimal();

        // when
        Meal meal = new Meal();
        meal.setId(id);
        meal.setTitle(title);
        meal.setCategory(category);
        meal.setDescription(description);
        meal.setEmoji(emoji);
        meal.setPrice(price);

        // then
        assertEquals(id, meal.getId());
        assertEquals(title, meal.getTitle());
        assertEquals(category, meal.getCategory());
        assertEquals(description, meal.getDescription());
        assertEquals(emoji, meal.getEmoji());
        assertEquals(price, meal.getPrice());
    }
}

