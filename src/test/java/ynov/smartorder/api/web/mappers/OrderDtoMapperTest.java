package ynov.smartorder.api.web.mappers;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ynov.smartorder.api.domain.models.Meal;
import ynov.smartorder.api.domain.models.Order;
import ynov.smartorder.api.web.dtos.MealDto;
import ynov.smartorder.api.web.dtos.OrderDto;
import ynov.smartorder.api.web.dtos.StateDto;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderDtoMapperTest {

    private final OrderDtoMapper mapper = Mappers.getMapper(OrderDtoMapper.class);

    @Test
    void shouldMapOrderToDto() {
        // given
        UUID orderId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        Order order = new Order();
        order.setId(orderId);
        order.setUserId(userId);
        order.setDate(now);
        order.setMeals(List.of(new Meal()));
        order.setState("PENDING");
        order.setTotal(39.90);

        // when
        OrderDto dto = mapper.toDto(order);

        // then
        assertNotNull(dto);
        assertEquals(orderId, dto.getId());
        assertEquals(userId, dto.getUserId());
        assertEquals(now, dto.getDate().toLocalDateTime());
        assertEquals(order.getMeals().size(), dto.getMeals().size());
        assertEquals("PENDING", dto.getState().name());
        assertEquals(39.90, dto.getTotal());
    }

    @Test
    void shouldMapDtoToOrder() {
        // given
        UUID orderId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        OffsetDateTime now = OffsetDateTime.now();

        OrderDto dto = new OrderDto();
        dto.setId(orderId);
        dto.setUserId(userId);
        dto.setDate(now);
        dto.setMeals(List.of(new MealDto()));
        dto.setState(StateDto.READY_FOR_PICKUP);
        dto.setTotal(25.75);

        // when
        Order order = mapper.toEntity(dto);

        // then
        assertNotNull(order);
        assertEquals(orderId, order.getId());
        assertEquals(userId, order.getUserId());
        assertEquals(now.toLocalDateTime(), order.getDate());
        assertEquals(dto.getMeals().size(), order.getMeals().size());
        assertEquals("READY_FOR_PICKUP", order.getState());
        assertEquals(25.75, order.getTotal());
    }
}
