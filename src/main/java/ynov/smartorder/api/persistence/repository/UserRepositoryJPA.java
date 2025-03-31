package ynov.smartorder.api.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import ynov.smartorder.api.persistence.entities.UserEty;

import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryJPA extends JpaRepository<UserEty, UUID> {

    Optional<UserEty> findByEmail(@NonNull String email);
}
