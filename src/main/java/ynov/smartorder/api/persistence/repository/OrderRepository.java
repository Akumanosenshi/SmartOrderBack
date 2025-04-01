package ynov.smartorder.api.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


import ynov.smartorder.api.domain.models.Order;
import ynov.smartorder.api.domain.ports.OrderPort;
import ynov.smartorder.api.persistence.entities.OrderEty;
import ynov.smartorder.api.persistence.entities.UserEty;
import ynov.smartorder.api.persistence.mappers.OrderEtyMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderRepository implements OrderPort {

    private final OrderRepositoryJPA orderRepositoryJPA;
    private final UserRepositoryJPA userRepositoryJPA;
    private final OrderEtyMapper orderEtyMapper;

    @Override
    public void saveOrder(Order order) {
        Optional<UserEty> userEtyOpt = userRepositoryJPA.findByEmail(order.getUser().getEmail());

        if (userEtyOpt.isEmpty()) {
            throw new IllegalArgumentException("Utilisateur introuvable pour l'email : " + order.getUser().getEmail());
        }

        OrderEty orderEty = orderEtyMapper.toEty(order);
        orderEty.setUser(userEtyOpt.get()); // 🟢 injecte une entité managée !

        orderRepositoryJPA.save(orderEty);
    }


    @Override
    public void deleteOrder(Order order) {
    orderRepositoryJPA.findById(order.getId()).ifPresent(orderRepositoryJPA::delete);
    }

    @Override
    public List<Order> getOrders(UUID Id) {
        return orderRepositoryJPA.findByUserId(Id)
                .stream()
                .map(orderEtyMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> getCurrentOrders(UUID Id) {
        return orderRepositoryJPA.findByUserId(Id)
                .stream()
                .filter((ety) -> !ety.getValidated())
                .map(orderEtyMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> getAllCurrentOrders() {
        return orderRepositoryJPA.findAll()
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
