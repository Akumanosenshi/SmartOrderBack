package ynov.smartorder.api.domain.ports;

import ynov.smartorder.api.domain.models.Restaurant;

import java.util.UUID;

public interface RestaurantPort {
    void saveRestaurant(Restaurant restaurant);

    void deleteRestaurant(UUID uuid);

    Restaurant findRestaurant(String email);
}
