package ynov.smartorder.api.persistence.mappers;

import org.mapstruct.Mapper;
import ynov.smartorder.api.domain.models.Meal;
import ynov.smartorder.api.persistence.entities.MealEty;

@Mapper
public interface MealEtyMapper {

    MealEty toEty(final Meal meal);

    Meal toModel(final MealEty mealEty);
}
