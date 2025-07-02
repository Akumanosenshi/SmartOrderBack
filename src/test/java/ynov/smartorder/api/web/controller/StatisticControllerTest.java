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
import ynov.smartorder.api.domain.models.Reservation;
import ynov.smartorder.api.domain.models.User;
import ynov.smartorder.api.domain.ports.UserPort;
import ynov.smartorder.api.persistence.repository.MealRepository;
import ynov.smartorder.api.persistence.repository.OrderRepository;
import ynov.smartorder.api.persistence.repository.ReservationRepository;
import ynov.smartorder.api.web.dtos.*;
import ynov.smartorder.api.web.mappers.*;
import ynov.smartorder.api.web.services.BruteForceProtectionService;
import ynov.smartorder.api.web.services.JwtService;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatisticControllerTest implements WithRandom {

    @Mock private OrderRepository orderRepository;
    @Mock private ReservationRepository reservationRepository;
    @Mock private StatisticDtoMapper statisticDtoMapper;
    @Mock private MealDtoMapper mealDtoMapper;

    @InjectMocks
    private StatisticController statisticController;

    @Test
    void shouldReturnStatistics() {
        // given
        OffsetDateTime start = OffsetDateTime.now().minusDays(7);
        OffsetDateTime end = OffsetDateTime.now();

        var meals = List.of(random(Meal.class));
        var mealDtos = List.of(random(MealDto.class));

        when(orderRepository.getTopMeals(any(), any())).thenReturn(meals);
        when(orderRepository.getTotalOrders(any(), any())).thenReturn(5);
        when(orderRepository.getTotalRevenue(any(), any())).thenReturn(250.0);
        when(orderRepository.getAverageCart(any(), any())).thenReturn(50.0);
        when(reservationRepository.getTotalReservations(any(), any())).thenReturn(3);
        when(reservationRepository.getAveragePeoplePerReservation(any(), any())).thenReturn(2);
        when(mealDtoMapper.toDto(any(Meal.class))).thenReturn(mealDtos.get(0));

        // when
        ResponseEntity<StatisticsDto> response = statisticController.statisticsGet(start, end);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        StatisticsDto stats = response.getBody();
        assertNotNull(stats);
        assertEquals(mealDtos, stats.getTopMeals());
        assertEquals(5, stats.getTotalOrders());
        assertEquals(250.0, stats.getTotalRevenue());
        assertEquals(50.0, stats.getAverageCart());
        assertEquals(3, stats.getTotalReservations());
        assertEquals(2, stats.getAveragePeoplePerReservation());
    }
}

