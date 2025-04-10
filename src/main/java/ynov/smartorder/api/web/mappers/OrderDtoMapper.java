package ynov.smartorder.api.web.mappers;

import org.mapstruct.Mapper;
import ynov.smartorder.api.domain.models.Order;
import ynov.smartorder.api.web.dtos.OrderDto;

@Mapper(componentModel = "spring", uses = DateDtoMapper.class)
public interface OrderDtoMapper {
    OrderDto toDto(Order order);
    Order toEntity(OrderDto orderDto);
}
