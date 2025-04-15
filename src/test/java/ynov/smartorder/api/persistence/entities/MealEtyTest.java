package ynov.smartorder.api.persistence.entities;

import org.junit.jupiter.api.Test;
import ynov.smartorder.api.common.utils.WithRandom;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
public class MealEtyTest implements WithRandom {

    @Test
    void testGettersAndSetters() {
        //given
        MealEty meal = new MealEty();
        UUID id = UUID.randomUUID();
        String title = randomString();
        String category = randomString();
        String description = randomString();
        String emoji = randomString();
        BigDecimal price = randomBigDecimal();
        //when
        meal.setId(id);
        meal.setTitle(title);
        meal.setCategory(category);
        meal.setDescription(description);
        meal.setEmoji(emoji);
        meal.setPrice(price);
        //then
        assertThat(meal).isNotNull();
        assertThat(meal.getId()).isEqualTo(id);
        assertThat(meal.getTitle()).isEqualTo(title);
        assertThat(meal.getCategory()).isEqualTo(category);
        assertThat(meal.getDescription()).isEqualTo(description);
        assertThat(meal.getEmoji()).isEqualTo(emoji);
        assertThat(meal.getPrice()).isEqualTo(price);
    }
}
