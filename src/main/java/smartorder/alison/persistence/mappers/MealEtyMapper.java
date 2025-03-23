package smartorder.alison.persistence.mappers;

import org.mapstruct.Mapper;
import smartorder.alison.domain.models.Meal;
import smartorder.alison.persistence.entities.MealEty;

@Mapper
public interface MealEtyMapper {

    MealEty toEty(final Meal meal);

    Meal toModel(final MealEty mealEty);
}
