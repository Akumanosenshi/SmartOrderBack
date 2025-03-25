package ynov.smartorder.api.web.mappers;

import org.mapstruct.Mapper;
import smartorder.alison_api.web.dtos.MealDto;
import ynov.smartorder.api.domain.models.Meal;

@Mapper
public interface MealDtoMapper {

    MealDto toDto(Meal meal);

    Meal toEntity(MealDto mealDto);
}
