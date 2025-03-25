package ynov.smartorder.api.web.mappers;

import smartorder.alison_api.web.dtos.OrderDto;
import org.mapstruct.Mapper;
import ynov.smartorder.api.domain.models.Order;

@Mapper
public interface OrderDtoMapper {
    OrderDto toDto(Order order);
    Order toEntity(OrderDto orderDto);
}
