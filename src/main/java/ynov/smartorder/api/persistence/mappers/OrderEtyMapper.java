package ynov.smartorder.api.persistence.mappers;

import org.mapstruct.Mapper;
import ynov.smartorder.api.domain.models.Order;
import ynov.smartorder.api.persistence.entities.OrderEty;

@Mapper(componentModel = "spring", uses = DateTimeEtyMapper.class)
public interface OrderEtyMapper {

     OrderEty toEty(final Order order);

     Order toModel(final OrderEty orderEty);
}
