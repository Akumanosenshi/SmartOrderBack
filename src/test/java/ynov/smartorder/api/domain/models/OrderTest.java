package ynov.smartorder.api.domain.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ynov.smartorder.api.common.utils.WithRandom;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class OrderTest implements WithRandom {

    @Test
    void shouldCreateOrderWithCorrectValues() {
        // given
        UUID id = randomUUID();
        LocalDateTime date = LocalDateTime.now();
        UUID userId = randomUUID();
        List<Meal> meals = List.of(random(Meal.class), random(Meal.class));
        String state = randomString();
        Double total = randomDouble();

        // when
        Order order = new Order();
        order.setId(id);
        order.setDate(date);
        order.setUserId(userId);
        order.setMeals(meals);
        order.setState(state);
        order.setTotal(total);

        // then
        assertEquals(id, order.getId());
        assertEquals(date, order.getDate());
        assertEquals(userId, order.getUserId());
        assertEquals(meals, order.getMeals());
        assertEquals(state, order.getState());
        assertEquals(total, order.getTotal());
    }
}
