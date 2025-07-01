package ynov.smartorder.api.persistence.entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ynov.smartorder.api.common.utils.WithRandom;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class OrderEtyTest implements WithRandom {

    @Test
    void shouldSetAndGetOrderEtyFieldsCorrectly() {
        // given
        UUID id = randomUUID();
        LocalDateTime date = LocalDateTime.now();
        UUID userId = randomUUID();
        List<MealEty> meals = List.of(random(MealEty.class), random(MealEty.class));
        String state = randomString();
        Double total = randomDouble();

        // when
        OrderEty orderEty = new OrderEty();
        orderEty.setId(id);
        orderEty.setDate(date);
        orderEty.setUserId(userId);
        orderEty.setMeals(meals);
        orderEty.setState(state);
        orderEty.setTotal(total);

        // then
        assertEquals(id, orderEty.getId());
        assertEquals(date, orderEty.getDate());
        assertEquals(userId, orderEty.getUserId());
        assertEquals(meals, orderEty.getMeals());
        assertEquals(state, orderEty.getState());
        assertEquals(total, orderEty.getTotal());
    }
}

