package smartorder.alison.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smartorder.alison.persistence.entities.MealEty;

public interface MealRepositoryJPA extends JpaRepository<MealEty, Long> {
}
