package smartorder.alison.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smartorder.alison.domain.models.Reservation;

import java.util.UUID;

public interface ReservationRepositoryJPA extends JpaRepository<Reservation, UUID> {
}
