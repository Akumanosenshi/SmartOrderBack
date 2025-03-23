package smartorder.alison.domain.ports;

import smartorder.alison.domain.models.Meal;

import java.util.List;

public interface MealPort {

    void createMeal(Meal meal);

    void updateMeal(Meal meal);

    void deleteMeal(String name);

    Meal getMeal(String name);

    List<Meal> getAllMeals();
}
