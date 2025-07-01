package ynov.smartorder.api.persistence.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ynov.smartorder.api.common.utils.WithRandom;
import ynov.smartorder.api.domain.models.Meal;
import ynov.smartorder.api.domain.models.Order;
import ynov.smartorder.api.persistence.entities.MealEty;
import ynov.smartorder.api.persistence.entities.OrderEty;
import ynov.smartorder.api.persistence.entities.UserEty;
import ynov.smartorder.api.persistence.mappers.BigDecimalEtyMapper;
import ynov.smartorder.api.persistence.mappers.MealEtyMapper;
import ynov.smartorder.api.persistence.mappers.OrderEtyMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderRepositoryTest implements WithRandom {

    @Mock
    private OrderRepositoryJPA orderRepositoryJPA;

    @Mock
    private UserRepositoryJPA userRepositoryJPA;

    @Mock
    private OrderEtyMapper orderEtyMapper;

    @Mock
    private MealRepositoryJPA mealRepositoryJPA;

    @Mock
    private MealEtyMapper mealEtyMapper;

    @Mock
    private BigDecimalEtyMapper bigDecimalEtyMapper;

    @InjectMocks
    private OrderRepository orderRepository;

    @Test
    void shouldSaveOrderCorrectly() {
        // given
        Order order = random(Order.class);
        UserEty userEty = random(UserEty.class);
        OrderEty orderEty = new OrderEty();

        Meal meal1 = random(Meal.class);
        Meal meal2 = random(Meal.class);
        order.setMeals(List.of(meal1, meal2));

        MealEty mealEty1 = random(MealEty.class);
        MealEty mealEty2 = random(MealEty.class);

        when(userRepositoryJPA.findById(order.getUserId())).thenReturn(Optional.of(userEty));
        when(orderEtyMapper.toEty(order)).thenReturn(orderEty);
        when(mealRepositoryJPA.findByTitle(meal1.getTitle())).thenReturn(Optional.of(mealEty1));
        when(mealRepositoryJPA.findByTitle(meal2.getTitle())).thenReturn(Optional.of(mealEty2));

        // when
        orderRepository.saveOrder(order);

        // then
        verify(orderRepositoryJPA).save(orderEty);
        assertEquals(List.of(mealEty1, mealEty2), orderEty.getMeals());
    }
}

