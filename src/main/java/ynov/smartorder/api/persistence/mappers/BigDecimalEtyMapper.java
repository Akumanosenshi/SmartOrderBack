package ynov.smartorder.api.persistence.mappers;

import org.mapstruct.Mapper;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface BigDecimalEtyMapper {


    default BigDecimal toBigDecimal(Double value) {
        return value != null ? BigDecimal.valueOf(value) : null;
    }

    default Double fromBigDecimal(BigDecimal value) {
        return value != null ? value.doubleValue() : null;
    }
}


