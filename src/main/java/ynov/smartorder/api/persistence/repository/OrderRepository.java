package ynov.smartorder.api.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import ynov.smartorder.api.domain.models.Meal;
import ynov.smartorder.api.domain.models.Order;
import ynov.smartorder.api.domain.ports.OrderPort;
import ynov.smartorder.api.persistence.entities.MealEty;
import ynov.smartorder.api.persistence.entities.OrderEty;
import ynov.smartorder.api.persistence.entities.UserEty;
import ynov.smartorder.api.persistence.mappers.BigDecimalEtyMapper;
import ynov.smartorder.api.persistence.mappers.MealEtyMapper;
import ynov.smartorder.api.persistence.mappers.OrderEtyMapper;
import ynov.smartorder.api.web.dtos.MealDto;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderRepository implements OrderPort {

    private final OrderRepositoryJPA orderRepositoryJPA;
    private final UserRepositoryJPA userRepositoryJPA;
    private final OrderEtyMapper orderEtyMapper;
    private final MealRepositoryJPA mealRepositoryJPA;
    private final MealEtyMapper mealEtyMapper;
    private final BigDecimalEtyMapper bigDecimalEtyMapper;



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
        return orderRepositoryJPA.findByValidated(false)
                .stream()
                .map(orderEtyMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> getAllOrder() {
        return orderRepositoryJPA.findAll()
                .stream()
                .map(orderEtyMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public void validateOrder(UUID id) {
        orderRepositoryJPA.findById(id)
                .ifPresent(orderEty -> {
                    orderEty.setValidated(true);
                    orderRepositoryJPA.save(orderEty);
                });
    }

    @Override
    public List<Meal> getTopMeals(LocalDateTime start, LocalDateTime end) {
        return orderRepositoryJPA.findAll()
                .stream()
                .filter(orderEty -> orderEty.getDate().isAfter(start) && orderEty.getDate().isBefore(end))
                .flatMap(orderEty -> orderEty.getMeals().stream())
                .collect(Collectors.groupingBy(MealEty::getTitle, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .map(entry -> mealRepositoryJPA.findByTitle(entry.getKey())
                        .map(mealEtyMapper::toModel)
                        .orElse(null))
                .collect(Collectors.toList());

    }

    @Override
    public int getTotalOrders(LocalDateTime start, LocalDateTime end) {
        return (int) orderRepositoryJPA.findAll()
                .stream()
                .filter(orderEty -> orderEty.getDate().isAfter(start) && orderEty.getDate().isBefore(end))
                .count();
    }

    @Override
    public double getTotalRevenue(LocalDateTime start, LocalDateTime end) {
        return orderRepositoryJPA.findAll()
                .stream()
                .filter(orderEty -> orderEty.getDate().isAfter(start) && orderEty.getDate().isBefore(end))
                .flatMap(orderEty -> orderEty.getMeals().stream())
                .mapToDouble(mealEty -> bigDecimalEtyMapper.fromBigDecimal(mealEty.getPrice()))
                .sum();
    }

    @Override
    public double getAverageCart(LocalDateTime start, LocalDateTime end) {
        List<OrderEty> orders = orderRepositoryJPA.findAll()
                .stream()
                .filter(orderEty -> orderEty.getDate().isAfter(start) && orderEty.getDate().isBefore(end))
                .collect(Collectors.toList());

        if (orders.isEmpty()) {
            return 0.0;
        }

        double totalRevenue = orders.stream()
                .flatMap(orderEty -> orderEty.getMeals().stream())
                .mapToDouble(mealEty -> bigDecimalEtyMapper.fromBigDecimal(mealEty.getPrice()))
                .sum();

        return totalRevenue / orders.size();
    }


}
