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

    void deleteOrder(UUID id);

    List<Order> getOrders(UUID Id);

    List<Order> getCurrentOrders();

    List<Order> getAllOrder();

    void validateOrder(UUID id);

    List<Meal> getTopMeals(LocalDateTime start, LocalDateTime end);

    int getTotalOrders(LocalDateTime start, LocalDateTime end);

    double getTotalRevenue(LocalDateTime start, LocalDateTime end);

    double getAverageCart(LocalDateTime start, LocalDateTime end);

}
