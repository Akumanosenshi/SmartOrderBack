package smartorder.alison.domain.ports;

import org.aspectj.weaver.ast.Or;
import smartorder.alison.domain.models.Order;

import java.util.List;
import java.util.UUID;

public interface OrderPort {
    void saveOrder(Order order);

    void deleteOrder(Order order);

    List<Order> getOrders(UUID userId);

    List<Order> getCurrentOrders(UUID userId);

    List<Order> getAllOrder();
}
