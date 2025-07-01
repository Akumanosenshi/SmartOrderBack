package ynov.smartorder.api.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ynov.smartorder.api.persistence.entities.ReservationEty;

import java.util.List;
import java.util.UUID;

public interface ReservationRepositoryJPA extends JpaRepository<ReservationEty, UUID> {

    List<ReservationEty> findByUserId(UUID userId);



}
