package ynov.smartorder.api.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
public class Statistic {

    private List<Meal> topMeals;
    private int totalOrders;
    private double totalRevenue;
    private double averageCart;
    private int totalReservations;
    private int averagePeoplePerReservation;

}

