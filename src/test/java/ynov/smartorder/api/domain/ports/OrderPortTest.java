package ynov.smartorder.api.domain.ports;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ynov.smartorder.api.common.utils.WithRandom;
import ynov.smartorder.api.domain.models.Meal;
import ynov.smartorder.api.domain.models.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderPortTest implements WithRandom {

    @Mock
    private OrderPort orderPort;

    @Test
    void shouldSaveOrder() {
        // given
        Order order = random(Order.class);

        // when
        orderPort.saveOrder(order);

        // then
        verify(orderPort).saveOrder(order);
    }

    @Test
    void shouldGetOrdersByUserId() {
        // given
        UUID userId = randomUUID();
        List<Order> expectedOrders = List.of(random(Order.class), random(Order.class));
        when(orderPort.getOrders(userId)).thenReturn(expectedOrders);

        // when
        List<Order> result = orderPort.getOrders(userId);

        // then
        assertEquals(expectedOrders, result);
    }

    @Test
    void shouldGetAllOrders() {
        // given
        List<Order> expectedOrders = List.of(random(Order.class));
        when(orderPort.getAllOrder()).thenReturn(expectedOrders);

        // when
        List<Order> result = orderPort.getAllOrder();

        // then
        assertEquals(expectedOrders, result);
    }

    @Test
    void shouldChangeOrderToInProgress() {
        // given
        UUID orderId = randomUUID();

        // when
        orderPort.toInProgress(orderId);

        // then
        verify(orderPort).toInProgress(orderId);
    }

    @Test
    void shouldChangeOrderToReadyToPickUp() {
        // given
        UUID orderId = randomUUID();

        // when
        orderPort.toReadyToPickUp(orderId);

        // then
        verify(orderPort).toReadyToPickUp(orderId);
    }

    @Test
    void shouldChangeOrderToCompleted() {
        // given
        UUID orderId = randomUUID();

        // when
        orderPort.toCompleted(orderId);

        // then
        verify(orderPort).toCompleted(orderId);
    }

    @Test
    void shouldChangeOrderToCancel() {
        // given
        UUID orderId = randomUUID();

        // when
        orderPort.toCancel(orderId);

        // then
        verify(orderPort).toCancel(orderId);
    }

    @Test
    void shouldReturnTopMeals() {
        // given
        LocalDateTime start = LocalDateTime.now().minusDays(7);
        LocalDateTime end = LocalDateTime.now();
        List<Meal> meals = List.of(random(Meal.class));
        when(orderPort.getTopMeals(start, end)).thenReturn(meals);

        // when
        List<Meal> result = orderPort.getTopMeals(start, end);

        // then
        assertEquals(meals, result);
    }

    @Test
    void shouldReturnTotalOrders() {
        // given
        LocalDateTime start = LocalDateTime.now().minusDays(7);
        LocalDateTime end = LocalDateTime.now();
        int total = randomInt();
        when(orderPort.getTotalOrders(start, end)).thenReturn(total);

        // when
        int result = orderPort.getTotalOrders(start, end);

        // then
        assertEquals(total, result);
    }

    @Test
    void shouldReturnTotalRevenue() {
        // given
        LocalDateTime start = LocalDateTime.now().minusDays(7);
        LocalDateTime end = LocalDateTime.now();
        double revenue = randomDouble();
        when(orderPort.getTotalRevenue(start, end)).thenReturn(revenue);

        // when
        double result = orderPort.getTotalRevenue(start, end);

        // then
        assertEquals(revenue, result);
    }

    @Test
    void shouldReturnAverageCart() {
        // given
        LocalDateTime start = LocalDateTime.now().minusDays(7);
        LocalDateTime end = LocalDateTime.now();
        double average = randomDouble();
        when(orderPort.getAverageCart(start, end)).thenReturn(average);

        // when
        double result = orderPort.getAverageCart(start, end);

        // then
        assertEquals(average, result);
    }
}
