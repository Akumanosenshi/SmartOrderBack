package ynov.smartorder.api.web.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import smartorder.alison_api.web.dtos.MealDto;
import ynov.smartorder.api.domain.models.Meal;

@Mapper(componentModel = "spring", uses = BigDecimalDtoMapper.class)
public interface MealDtoMapper {

    @Mapping(target = "emoji", source = "emoji")
    MealDto toDto(Meal meal);

    @Mapping(target = "emoji", source = "emoji")
    Meal toEntity(MealDto mealDto);
}
