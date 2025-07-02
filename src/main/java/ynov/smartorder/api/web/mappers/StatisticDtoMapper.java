package ynov.smartorder.api.web.mappers;

import org.mapstruct.Mapper;
import ynov.smartorder.api.domain.models.Statistic;
import ynov.smartorder.api.web.dtos.StatisticsDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface StatisticDtoMapper {

    default OffsetDateTime toOffsetDateTime(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.atOffset(ZoneOffset.UTC) : null;
    }

    default LocalDateTime toLocalDateTime(OffsetDateTime offsetDateTime) {
        return offsetDateTime != null ? offsetDateTime.toLocalDateTime() : null;
    }

    default Double fromBigDecimal(BigDecimal value) {
        return value != null ? value.doubleValue() : null;
    }

    default BigDecimal toBigDecimal(Double value) {
        return value != null ? BigDecimal.valueOf(value) : null;
    }

    StatisticsDto toDto(Statistic meal);

    Statistic toEntity(StatisticsDto mealDto);
}


