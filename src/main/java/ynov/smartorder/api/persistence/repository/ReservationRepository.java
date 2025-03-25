package ynov.smartorder.api.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import ynov.smartorder.api.domain.models.Reservation;
import ynov.smartorder.api.domain.ports.ReservationPort;
import ynov.smartorder.api.persistence.mappers.ReservationEtyMapper;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ReservationRepository implements ReservationPort {
    private final ReservationRepositoryJPA reservationRepositoryJPA;
    private final ReservationEtyMapper reservationEtyMapper;

    @Override
    public void saveReservation(Reservation reservation) {
        reservationRepositoryJPA.save(reservationEtyMapper.toEty(reservation));
    }
    @Override
    public void deleteReservation(UUID uuid) {
        reservationRepositoryJPA.findById(uuid).ifPresent(reservationRepositoryJPA::delete);
    }

    @Override
    public void updateReservation(Reservation reservation) {
        reservationRepositoryJPA.save(reservationEtyMapper.toEty(reservation));
    }

    @Override
    public Reservation FindReservation(UUID userId) {
        return reservationRepositoryJPA.findById(userId).map(reservationEtyMapper::toModel).orElse(null);
    }

    @Override
    public List<Reservation> FindAllReservation() {
        return reservationRepositoryJPA.findAll().stream().map(reservationEtyMapper::toModel).toList();
    }

}
