package ynov.smartorder.api.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ynov.smartorder.api.persistence.entities.UserEty;

import java.util.UUID;

public interface UserRepositoryJPA extends JpaRepository<UserEty, UUID> {
}
