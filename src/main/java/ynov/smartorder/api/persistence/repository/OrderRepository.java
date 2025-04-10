package ynov.smartorder.api.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import ynov.smartorder.api.domain.models.Order;
import ynov.smartorder.api.domain.ports.OrderPort;
import ynov.smartorder.api.persistence.entities.MealEty;
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
    @Autowired
    private final MealRepositoryJPA mealRepositoryJPA;


    @Override
    public void saveOrder(Order order) {
        // 1. Récupère l'utilisateur managé
        UserEty user = userRepositoryJPA.findByEmail(order.getUser().getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable"));

        // 2. Mapper la commande (sans user ni meals)
        OrderEty orderEty = orderEtyMapper.toEty(order);
        orderEty.setUser(user);

        // 3. 🧠 Pour chaque plat, chercher le MealEty déjà en DB (par titre, id ou autre)
        List<MealEty> meals = order.getMeals().stream()
                .map(meal -> mealRepositoryJPA.findByTitle(meal.getTitle()) // ou .findById(...)
                        .orElseThrow(() -> new IllegalArgumentException("Plat non trouvé : " + meal.getTitle())))
                .collect(Collectors.toList());

        // 4. Injection des plats existants
        orderEty.setMeals(meals);

        // 5. Save !
        orderRepositoryJPA.save(orderEty);
    }



    @Override
    public void deleteOrder(UUID id) {
        orderRepositoryJPA.findById(id)
                .ifPresent(orderRepositoryJPA::delete);
    }

    @Override
    public List<Order> getOrders(UUID Id) {
        return orderRepositoryJPA.findByUserId(Id)
                .stream()
                .map(orderEtyMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> getCurrentOrders() {
        return orderRepositoryJPA.findByValidated(true)
                .stream()
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
