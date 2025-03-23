package smartorder.alison.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smartorder.alison.domain.models.User;

import java.util.UUID;

public interface UserRepositoryJPA extends JpaRepository<User, UUID> {
}
