package ynov.smartorder.api.persistence.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ynov.smartorder.api.domain.models.Meal;
import ynov.smartorder.api.persistence.entities.MealEty;

@Mapper
public interface MealEtyMapper {

    @Mapping(target = "emoji", source = "emoji")
    MealEty toEty(final Meal meal);

    @Mapping(target = "emoji", source = "emoji")
    Meal toModel(final MealEty mealEty);
}
