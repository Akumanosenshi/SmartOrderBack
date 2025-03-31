package ynov.smartorder.api.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ynov.smartorder.api.domain.models.Restaurant;
import ynov.smartorder.api.domain.ports.RestaurantPort;
import ynov.smartorder.api.persistence.entities.RestaurantEty;
import ynov.smartorder.api.persistence.mappers.RestaurantEtyMapper;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RestaurantRepository implements RestaurantPort {

    private final RestaurantRepositoryJPA restaurantRepositoryJPA;
    private final RestaurantEtyMapper restaurantMapper;

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        restaurantRepositoryJPA.save(restaurantMapper.toEty(restaurant));
    }

    @Override
    public void deleteRestaurant(UUID uuid) {
        restaurantRepositoryJPA.findById(uuid).ifPresent(restaurantRepositoryJPA::delete);
    }

    @Override
    public Restaurant findRestaurant(String email) {
        return restaurantRepositoryJPA.findByEmail(email)
                .map(restaurantMapper::toModel)
                .orElse(null);
    }
}
