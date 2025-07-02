package ynov.smartorder.api.web.mappers;

import org.mapstruct.Mapper;
import ynov.smartorder.api.domain.models.Order;
import ynov.smartorder.api.web.dtos.OrderDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ynov.smartorder.api.domain.models.Order;
import ynov.smartorder.api.web.dtos.OrderDto;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface OrderDtoMapper {

    @Mappings({
            @Mapping(target = "date", expression = "java(toOffsetDateTime(order.getDate()))")
    })
    OrderDto toDto(Order order);

    @Mappings({
            @Mapping(target = "date", expression = "java(toLocalDateTime(orderDto.getDate()))")
    })
    Order toEntity(OrderDto orderDto);

    // Méthodes de conversion date intégrées
    default OffsetDateTime toOffsetDateTime(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.atOffset(ZoneOffset.UTC) : null;
    }

    default LocalDateTime toLocalDateTime(OffsetDateTime offsetDateTime) {
        return offsetDateTime != null ? offsetDateTime.toLocalDateTime() : null;
    }
}
