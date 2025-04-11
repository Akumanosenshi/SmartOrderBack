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

    List<Order> getAllCurrentOrders();

    List<Order> getAllOrder();


}
