package smartorder.alison.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import smartorder.alison.domain.models.Order;
import smartorder.alison.domain.ports.OrderPort;
import smartorder.alison.persistence.mappers.OrderEtyMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderRepository implements OrderPort {

    private final OrderRepositoryJPA orderRepositoryJPA;
    private final OrderEtyMapper orderEtyMapper;

    @Override
    public void saveOrder(Order order) {
        orderRepositoryJPA.save(orderEtyMapper.toEty(order));
    }

    @Override
    public void deleteOrder(Order order) {
    orderRepositoryJPA.findById(order.getId()).ifPresent(orderRepositoryJPA::delete);
    }

    @Override
    public List<Order> getOrders(UUID userId) {
        return orderRepositoryJPA.findByUserId(userId)
                .stream()
                .map(orderEtyMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> getCurrentOrders(UUID userId) {
        return orderRepositoryJPA.findByUserId(userId)
                .stream()
                .filter((ety) -> !ety.getValidated())
                .map(orderEtyMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> getAllOrder() {
        return orderRepositoryJPA.findAll().stream().map(orderEtyMapper::toModel).collect(Collectors.toList());
    }
}
