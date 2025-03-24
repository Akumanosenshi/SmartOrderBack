package smartorder.alison.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import smartorder.alison.persistence.entities.MealEty;

import java.util.Optional;
import java.util.UUID;


public interface MealRepositoryJPA extends JpaRepository<MealEty, UUID> {
    Optional<MealEty> findByTitle(@NonNull String title);
}
