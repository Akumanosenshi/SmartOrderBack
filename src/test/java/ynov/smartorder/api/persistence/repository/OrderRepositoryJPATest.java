package ynov.smartorder.api.persistence.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ynov.smartorder.api.common.utils.WithRandom;
import ynov.smartorder.api.persistence.entities.OrderEty;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderRepositoryJPATest implements WithRandom {

    @Mock
    private OrderRepositoryJPA orderRepositoryJPA;

    @Test
    void shouldFindOrdersByUserId() {
        // given
        UUID userId = randomUUID();
        List<OrderEty> expectedOrders = List.of(random(OrderEty.class), random(OrderEty.class));
        when(orderRepositoryJPA.findByUserId(userId)).thenReturn(expectedOrders);

        // when
        List<OrderEty> result = orderRepositoryJPA.findByUserId(userId);

        // then
        assertEquals(expectedOrders, result);
    }
}

