package ynov.smartorder.api.persistence.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ynov.smartorder.api.domain.models.Order;
import ynov.smartorder.api.persistence.entities.OrderEty;

@Mapper(componentModel = "spring", uses = DateTimeEtyMapper.class)
public interface OrderEtyMapper {

     @Mapping(target = "user", ignore = true)
     @Mapping(target = "meals", ignore = true)
     OrderEty toEty(final Order order);

     Order toModel(final OrderEty orderEty);
}
