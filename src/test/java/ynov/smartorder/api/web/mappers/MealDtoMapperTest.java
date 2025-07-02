package ynov.smartorder.api.web.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ynov.smartorder.api.domain.models.Meal;
import ynov.smartorder.api.web.dtos.MealDto;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
class MealDtoMapperTest {

    private final MealDtoMapper mapper = Mappers.getMapper(MealDtoMapper.class);

    @Test
    void shouldMapMealToDto() {
        Meal meal = new Meal();
        meal.setTitle("Burger");
        meal.setCategory("Fast Food");
        meal.setDescription("Double Cheese");
        meal.setEmoji("🍔");
        meal.setPrice(BigDecimal.valueOf(9.99));

        MealDto dto = mapper.toDto(meal);

        assertNotNull(dto);
        assertEquals("Burger", dto.getTitle());
        assertEquals("Fast Food", dto.getCategory());
        assertEquals("Double Cheese", dto.getDescription());
        assertEquals("🍔", dto.getEmoji());
        assertEquals(9.99, dto.getPrice());
    }

    @Test
    void shouldMapDtoToMeal() {
        MealDto dto = new MealDto();
        dto.setTitle("Sushi");
        dto.setCategory("Japonais");
        dto.setDescription("Saumon");
        dto.setEmoji("🍣");
        dto.setPrice(12.5);

        Meal meal = mapper.toEntity(dto);

        assertNotNull(meal);
        assertEquals("Sushi", meal.getTitle());
        assertEquals("Japonais", meal.getCategory());
        assertEquals("Saumon", meal.getDescription());
        assertEquals("🍣", meal.getEmoji());
        assertEquals(BigDecimal.valueOf(12.5), meal.getPrice());
    }
}
