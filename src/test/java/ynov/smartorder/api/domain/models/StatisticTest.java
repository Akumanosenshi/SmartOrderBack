package ynov.smartorder.api.domain.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ynov.smartorder.api.common.utils.WithRandom;

import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class StatisticTest implements WithRandom {

    @Test
    void shouldBuildStatisticWithCorrectValues() {
        // given
        List<Meal> topMeals = List.of(random(Meal.class), random(Meal.class));
        int totalOrders = randomInt();
        double totalRevenue = randomDouble();
        double averageCart = randomDouble();
        int totalReservations = randomInt();
        int averagePeople = randomInt();

        // when
        Statistic statistic = Statistic.builder()
                .topMeals(topMeals)
                .totalOrders(totalOrders)
                .totalRevenue(totalRevenue)
                .averageCart(averageCart)
                .totalReservations(totalReservations)
                .averagePeoplePerReservation(averagePeople)
                .build();

        // then
        assertEquals(topMeals, statistic.getTopMeals());
        assertEquals(totalOrders, statistic.getTotalOrders());
        assertEquals(totalRevenue, statistic.getTotalRevenue());
        assertEquals(averageCart, statistic.getAverageCart());
        assertEquals(totalReservations, statistic.getTotalReservations());
        assertEquals(averagePeople, statistic.getAveragePeoplePerReservation());
    }

    @Test
    void shouldSetAndGetFieldsCorrectly() {
        // given
        Statistic statistic = new Statistic();
        List<Meal> topMeals = List.of(random(Meal.class));
        int totalOrders = randomInt();
        double totalRevenue = randomDouble();
        double averageCart = randomDouble();
        int totalReservations = randomInt();
        int averagePeople = randomInt();

        // when
        statistic.setTopMeals(topMeals);
        statistic.setTotalOrders(totalOrders);
        statistic.setTotalRevenue(totalRevenue);
        statistic.setAverageCart(averageCart);
        statistic.setTotalReservations(totalReservations);
        statistic.setAveragePeoplePerReservation(averagePeople);

        // then
        assertEquals(topMeals, statistic.getTopMeals());
        assertEquals(totalOrders, statistic.getTotalOrders());
        assertEquals(totalRevenue, statistic.getTotalRevenue());
        assertEquals(averageCart, statistic.getAverageCart());
        assertEquals(totalReservations, statistic.getTotalReservations());
        assertEquals(averagePeople, statistic.getAveragePeoplePerReservation());
    }
}
