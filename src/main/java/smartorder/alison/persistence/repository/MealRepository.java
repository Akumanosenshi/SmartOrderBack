package smartorder.alison.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import smartorder.alison.domain.models.Meal;
import smartorder.alison.domain.ports.MealPort;
import smartorder.alison.persistence.mappers.MealEtyMapper;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MealRepository implements MealPort {

    private final MealRepositoryJPA mealRepositoryJPA;
    private final MealEtyMapper mealEtyMapper;

    @Override
    public void createMeal(Meal meal) {
        mealRepositoryJPA.save(mealEtyMapper.toEty(meal));

    }

    @Override
    public void updateMeal(Meal meal) {
        mealRepositoryJPA.save(mealEtyMapper.toEty(meal));
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
}
