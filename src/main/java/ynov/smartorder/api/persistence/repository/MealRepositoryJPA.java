package ynov.smartorder.api.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import ynov.smartorder.api.persistence.entities.MealEty;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface MealRepositoryJPA extends JpaRepository<MealEty, UUID> {
    Optional<MealEty> findByTitle(@NonNull String title);

    @Query("SELECT DISTINCT m.category FROM MealEty m")
    List<String> findDistinctCategories();
}
