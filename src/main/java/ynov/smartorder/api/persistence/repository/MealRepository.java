package ynov.smartorder.api.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ynov.smartorder.api.domain.models.Meal;
import ynov.smartorder.api.domain.ports.MealPort;
import ynov.smartorder.api.persistence.entities.MealEty;
import ynov.smartorder.api.persistence.mappers.MealEtyMapper;
import ynov.smartorder.api.persistence.repository.MealRepositoryJPA;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MealRepository implements MealPort {

    private final MealRepositoryJPA mealRepositoryJPA;
    private final MealEtyMapper mealEtyMapper;

    @Override
    public void createMeal(Meal meal) {
        MealEty mealEntity = mealEtyMapper.toEty(meal);
        mealEntity.setEmoji(meal.getEmoji());
        mealRepositoryJPA.save(mealEtyMapper.toEty(meal));

    }

    @Override
    public void updateMeal(Meal meal) {
        if (meal.getTitle() != null) {
            mealRepositoryJPA.findByTitle(meal.getTitle())
                    .ifPresent(existingMealEntity -> {
                        // Mise à jour champ par champ
                        existingMealEntity.setCategory(meal.getCategory());
                        existingMealEntity.setDescription(meal.getDescription());
                        existingMealEntity.setEmoji(meal.getEmoji());
                        existingMealEntity.setPrice(meal.getPrice());
                        // ⚠️ On modifies pas le title ici car sert de clé métier
                        mealRepositoryJPA.save(existingMealEntity);
                    });
        }
    }

    @Override
    public void deleteMeal(String title) {
        mealRepositoryJPA.findByTitle(title)
                .ifPresent(mealRepositoryJPA::delete);
    }

    @Override
    public Meal getMeal(String title) {
        return mealRepositoryJPA.findByTitle(title)
                .map(mealEtyMapper::toModel)
                .orElse(null);
    }

    @Override
    public List<Meal> getAllMeals() {
        return mealRepositoryJPA.findAll().stream()
                .map(mealEtyMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> GetAllCategories() {
        return mealRepositoryJPA.findDistinctCategories();
    }

}
