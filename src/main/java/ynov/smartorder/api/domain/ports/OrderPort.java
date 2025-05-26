package ynov.smartorder.api.domain.ports;

import org.aspectj.weaver.ast.Or;
import ynov.smartorder.api.domain.models.Meal;
import ynov.smartorder.api.domain.models.Order;
import ynov.smartorder.api.web.dtos.MealDto;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface OrderPort {
    void saveOrder(Order order);



    List<Order> getOrders(UUID Id);

    List<Order> getAllOrder();

    void toInProgress(UUID id);
    void toReadyToPickUp(UUID id);
    void toCompleted(UUID id);
    void toCancel(UUID id);


    List<Meal> getTopMeals(LocalDateTime start, LocalDateTime end);

    int getTotalOrders(LocalDateTime start, LocalDateTime end);

    double getTotalRevenue(LocalDateTime start, LocalDateTime end);

    double getAverageCart(LocalDateTime start, LocalDateTime end);

}
