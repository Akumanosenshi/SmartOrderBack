package ynov.smartorder.api.web.mappers;

import org.mapstruct.Mapper;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface BigDecimalDtoMapper {

    default BigDecimal toBigDecimal(Double value) {
        return value != null ? BigDecimal.valueOf(value) : null;
    }

    default Double fromBigDecimal(BigDecimal value) {
        return value != null ? value.doubleValue() : null;
    }
}
