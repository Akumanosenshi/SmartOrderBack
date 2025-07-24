package ynov.smartorder.api.web.controller;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ynov.smartorder.api.persistence.repository.OrderRepository;
import ynov.smartorder.api.persistence.repository.ReservationRepository;
import ynov.smartorder.api.web.apis.StatisticsApi;
import ynov.smartorder.api.web.dtos.StatisticsDto;
import ynov.smartorder.api.web.mappers.MealDtoMapper;
import ynov.smartorder.api.web.mappers.StatisticDtoMapper;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class StatisticController implements StatisticsApi {

    private final OrderRepository orderRepository;
    private final ReservationRepository reservationRepository;
    private final StatisticDtoMapper statisticDtoMapper;
    private final MealDtoMapper mealDtoMapper;
    private final MeterRegistry meterRegistry;

    @Override
    public ResponseEntity<StatisticsDto> statisticsGet(OffsetDateTime startDate, OffsetDateTime endDate) {

        StatisticsDto statistics = new StatisticsDto();
        statistics.setTopMeals(orderRepository.getTopMeals(startDate.toLocalDateTime(), endDate.toLocalDateTime()).stream().map(mealDtoMapper::toDto).collect(Collectors.toList()));
        statistics.setTotalOrders(orderRepository.getTotalOrders(startDate.toLocalDateTime(), endDate.toLocalDateTime()));
        statistics.setTotalRevenue(orderRepository.getTotalRevenue(startDate.toLocalDateTime(), endDate.toLocalDateTime()));
        statistics.setAverageCart(orderRepository.getAverageCart(startDate.toLocalDateTime(), endDate.toLocalDateTime()));
        statistics.setTotalReservations(reservationRepository.getTotalReservations(startDate.toLocalDateTime(), endDate.toLocalDateTime()));
        statistics.averagePeoplePerReservation(reservationRepository.getAveragePeoplePerReservation(startDate.toLocalDateTime(), endDate.toLocalDateTime()));

        return ResponseEntity.ok(statistics);

    }
}
