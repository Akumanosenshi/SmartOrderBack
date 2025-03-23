package smartorder.alison.web.mappers;

import org.mapstruct.Mapper;
import smartorder.alison.domain.models.Meal;
import smartorder.alison.web.dtos.MealDto;

@Mapper
public interface MealDtoMapper {

    MealDto toDto(Meal meal);

    Meal toEntity(MealDto mealDto);
}
