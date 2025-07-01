package ynov.smartorder.api.persistence.mappers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import ynov.smartorder.api.common.utils.WithRandom;
import ynov.smartorder.api.domain.models.Order;
import ynov.smartorder.api.persistence.entities.MealEty;
import ynov.smartorder.api.persistence.entities.OrderEty;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderEtyMapperTest implements WithRandom {

    private final OrderEtyMapper mapper = Mappers.getMapper(OrderEtyMapper.class);

    @Test
    void shouldMapOrderToOrderEtyWithoutMeals() {
        // given
        Order order = random(Order.class);

        // when
        OrderEty result = mapper.toEty(order);

        // then
        assertNotNull(result);
        assertEquals(order.getId(), result.getId());
        assertEquals(order.getDate(), result.getDate());
        assertEquals(order.getUserId(), result.getUserId());
        assertNull(result.getMeals()); // meals is ignored
        assertEquals(order.getState(), result.getState());
        assertEquals(order.getTotal(), result.getTotal());
    }

    @Test
    void shouldMapOrderEtyToOrder() {
        // given
        OrderEty orderEty = random(OrderEty.class);
        List<MealEty> meals = List.of(random(MealEty.class));
        orderEty.setMeals(meals);

        // when
        Order result = mapper.toModel(orderEty);

        // then
        assertNotNull(result);
        assertEquals(orderEty.getId(), result.getId());
        assertEquals(orderEty.getDate(), result.getDate());
        assertEquals(orderEty.getUserId(), result.getUserId());
        assertEquals(orderEty.getState(), result.getState());
        assertEquals(orderEty.getTotal(), result.getTotal());
    }
}
