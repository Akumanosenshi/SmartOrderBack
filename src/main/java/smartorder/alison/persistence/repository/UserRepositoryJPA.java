package smartorder.alison.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smartorder.alison.persistence.entities.UserEty;

import java.util.UUID;

public interface UserRepositoryJPA extends JpaRepository<UserEty, UUID> {
}
