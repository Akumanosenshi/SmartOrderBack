package smartorder.alison.web.mappers;

import org.mapstruct.Mapper;
import smartorder.alison.domain.models.Order;
import smartorder.alison.web.dtos.OrderDto;

@Mapper
public interface OrderDtoMapper {
    OrderDto toDto(Order order);
    Order toEntity(OrderDto orderDto);
}
