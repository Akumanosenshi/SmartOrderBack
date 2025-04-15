package ynov.smartorder.api.web.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ynov.smartorder.api.domain.models.Meal;
import ynov.smartorder.api.domain.models.Statistic;
import ynov.smartorder.api.web.dtos.MealDto;
import ynov.smartorder.api.web.dtos.StatisticsDto;

@Mapper(componentModel = "spring", uses = {DateDtoMapper.class, BigDecimalDtoMapper.class})
public interface StatisticDtoMapper {

        StatisticsDto toDto(Statistic meal);


        Statistic toEntity(StatisticsDto mealDto);
    }


