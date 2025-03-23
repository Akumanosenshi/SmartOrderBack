package smartorder.alison.persistence.mappers;

import org.mapstruct.Mapper;
import smartorder.alison.domain.models.Order;
import smartorder.alison.persistence.entities.OrderEty;

@Mapper
public interface OrderEtyMapper {

     OrderEty toEty(final Order order);

     Order toModel(final OrderEty orderEty);
}
