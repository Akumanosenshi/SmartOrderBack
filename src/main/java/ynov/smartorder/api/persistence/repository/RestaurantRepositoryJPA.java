package ynov.smartorder.api.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import ynov.smartorder.api.persistence.entities.ReservationEty;
import ynov.smartorder.api.persistence.entities.RestaurantEty;
import ynov.smartorder.api.persistence.entities.UserEty;

import java.util.Optional;
import java.util.UUID;

public interface RestaurantRepositoryJPA extends JpaRepository<RestaurantEty, UUID> {

    Optional<RestaurantEty> findByEmail(@NonNull String email);
}
