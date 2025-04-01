package ynov.smartorder.api.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import ynov.smartorder.api.persistence.entities.OrderEty;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface OrderRepositoryJPA extends JpaRepository<OrderEty, UUID> {
    List<OrderEty> findByUserId(@NonNull UUID studentId);

    List<OrderEty> findByValidated(Boolean validated);

}
