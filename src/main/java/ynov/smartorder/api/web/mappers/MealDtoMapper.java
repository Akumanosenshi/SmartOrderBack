package ynov.smartorder.api.web.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ynov.smartorder.api.domain.models.Meal;
import ynov.smartorder.api.web.dtos.MealDto;

@Mapper(componentModel = "spring", uses = BigDecimalDtoMapper.class)
public interface MealDtoMapper {

    @Mapping(target = "emoji", source = "emoji")
    MealDto toDto(Meal meal);

    @Mapping(target = "emoji", source = "emoji")
    Meal toEntity(MealDto mealDto);
}
