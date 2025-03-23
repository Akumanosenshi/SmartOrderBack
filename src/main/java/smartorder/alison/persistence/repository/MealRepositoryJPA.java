package smartorder.alison.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import smartorder.alison.persistence.entities.MealEty;

import java.util.Optional;

public interface MealRepositoryJPA extends JpaRepository<MealEty, Long> {
    Optional<MealEty> findByName(@NonNull String name);
}
