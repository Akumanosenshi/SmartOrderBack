package ynov.smartorder.api.persistence.mappers;


import org.mapstruct.Mapper;
import ynov.smartorder.api.domain.models.Restaurant;
import ynov.smartorder.api.persistence.entities.RestaurantEty;

@Mapper
public interface RestaurantEtyMapper {

    RestaurantEty toEty(final Restaurant restaurant);

    Restaurant toModel(final RestaurantEty restaurantEty);
}
