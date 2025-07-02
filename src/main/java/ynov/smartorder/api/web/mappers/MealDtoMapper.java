package ynov.smartorder.api.web.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ynov.smartorder.api.domain.models.Meal;
import ynov.smartorder.api.web.dtos.MealDto;

@Mapper(componentModel = "spring")
public interface MealDtoMapper {

    @Mapping(target = "price", expression = "java(meal.getPrice() != null ? meal.getPrice().doubleValue() : null)")
    MealDto toDto(Meal meal);

    @Mapping(target = "price", expression = "java(mealDto.getPrice() != null ? java.math.BigDecimal.valueOf(mealDto.getPrice()) : null)")
    Meal toEntity(MealDto mealDto);
}
