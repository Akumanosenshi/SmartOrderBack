package smartorder.alison.domain.ports;

import org.aspectj.weaver.ast.Or;
import smartorder.alison.domain.models.Order;

public interface OrderPort {
    void saveOrder(Order order);

    void deleteOrder(Order order);
}
