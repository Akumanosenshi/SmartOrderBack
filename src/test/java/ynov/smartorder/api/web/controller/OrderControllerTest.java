package ynov.smartorder.api.web.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ynov.smartorder.api.common.utils.WithRandom;
import ynov.smartorder.api.domain.models.Meal;
import ynov.smartorder.api.domain.models.Order;
import ynov.smartorder.api.domain.models.User;
import ynov.smartorder.api.domain.ports.UserPort;
import ynov.smartorder.api.persistence.repository.MealRepository;
import ynov.smartorder.api.persistence.repository.OrderRepository;
import ynov.smartorder.api.web.dtos.*;
import ynov.smartorder.api.web.mappers.MealDtoMapper;
import ynov.smartorder.api.web.mappers.OrderDtoMapper;
import ynov.smartorder.api.web.mappers.UserDtoMapper;
import ynov.smartorder.api.web.services.BruteForceProtectionService;
import ynov.smartorder.api.web.services.JwtService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest implements WithRandom {

    @Mock private OrderRepository orderRepository;
    @Mock private OrderDtoMapper orderDtoMapper;

    @InjectMocks
    private OrderController orderController;

    @Test
    void shouldReturnAllOrders() {
        // given
        List<Order> orders = List.of(random(Order.class));
        List<OrderDto> orderDtos = List.of(random(OrderDto.class));

        when(orderRepository.getAllOrder()).thenReturn(orders);
        when(orderDtoMapper.toDto(any(Order.class))).thenReturn(orderDtos.get(0));

        // when
        ResponseEntity<List<OrderDto>> response = orderController.orderAllGet();

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orderDtos, response.getBody());
    }

    @Test
    void shouldReturnNoContentIfNoOrders() {
        // given
        when(orderRepository.getAllOrder()).thenReturn(List.of());

        // when
        ResponseEntity<List<OrderDto>> response = orderController.orderAllGet();

        // then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void shouldSaveOrderWithPendingState() {
        // given
        OrderDto dto = random(OrderDto.class);
        Order order = random(Order.class);
        when(orderDtoMapper.toEntity(any())).thenReturn(order);

        // when
        ResponseEntity<Void> response = orderController.orderPost(dto);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(StateDto.PENDING, dto.getState());
        verify(orderRepository).saveOrder(order);
    }

    @Test
    void shouldReturnOrdersByUserId() {
        // given
        UUID userId = randomUUID();
        List<Order> orders = List.of(random(Order.class));
        OrderDto dto = random(OrderDto.class);
        when(orderRepository.getOrders(userId)).thenReturn(orders);
        when(orderDtoMapper.toDto(any())).thenReturn(dto);

        // when
        ResponseEntity<List<OrderDto>> response = orderController.orderUserGet(userId);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(dto, response.getBody().get(0));
    }

    @Test
    void shouldReturnNoContentIfNoOrdersByUserId() {
        // given
        UUID userId = randomUUID();
        when(orderRepository.getOrders(userId)).thenReturn(List.of());

        // when
        ResponseEntity<List<OrderDto>> response = orderController.orderUserGet(userId);

        // then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @ParameterizedTest
    @EnumSource(value = StateDto.class, names = {"IN_PROGRESS", "READY_FOR_PICKUP", "COMPLETED", "CANCELLED"})
    void shouldChangeOrderState(StateDto state) {
        // given
        UUID id = randomUUID();

        // when
        ResponseEntity<Void> response = orderController.changeOrderState(id, state);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());

        switch (state) {
            case IN_PROGRESS:
                verify(orderRepository).toInProgress(id);
                break;
            case READY_FOR_PICKUP:
                verify(orderRepository).toReadyToPickUp(id);
                break;
            case COMPLETED:
                verify(orderRepository).toCompleted(id);
                break;
            case CANCELLED:
                verify(orderRepository).toCancel(id);
                break;
        }
    }

}
