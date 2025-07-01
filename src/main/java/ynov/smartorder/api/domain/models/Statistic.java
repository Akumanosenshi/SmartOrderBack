package ynov.smartorder.api.domain.models;

import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Statistic {

    private List<Meal> topMeals;
    private int totalOrders;
    private double totalRevenue;
    private double averageCart;
    private int totalReservations;
    private int averagePeoplePerReservation;

}

