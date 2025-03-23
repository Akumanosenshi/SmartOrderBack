package smartorder.alison.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import smartorder.alison.domain.models.Order;
import smartorder.alison.domain.ports.OrderPort;
import smartorder.alison.persistence.entities.OrderEty;
import smartorder.alison.persistence.mappers.MealEtyMapper;
import smartorder.alison.persistence.mappers.OrderEtyMapper;

@Repository
@RequiredArgsConstructor
public class OrderRepository implements OrderPort {

    private final OrderRepositoryJPA orderRepositoryJPA;
    private final OrderEtyMapper orderEtyMapper;

    @Override
    public void saveOrder(Order order) {
        orderRepositoryJPA.save(order);
    }

    @Override
    public void deleteOrder(Order order) {
    orderRepositoryJPA.findById(order.getId()).ifPresent(orderRepositoryJPA::delete);
    }
}
