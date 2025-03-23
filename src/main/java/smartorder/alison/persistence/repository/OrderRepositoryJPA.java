package smartorder.alison.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smartorder.alison.domain.models.Order;

import java.util.UUID;

public interface OrderRepositoryJPA extends JpaRepository<Order, UUID> {

}
