package smartorder.alison.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import smartorder.alison.domain.models.Reservation;
import smartorder.alison.domain.ports.ReservationPort;
import smartorder.alison.persistence.mappers.ReservationEtyMapper;

@Repository
@RequiredArgsConstructor
public class ReservationRepository implements ReservationPort {
    private final ReservationRepositoryJPA reservationRepositoryJPA;
    private final ReservationEtyMapper reservationEtyMapper;
    @Override
    public void saveReservation(Reservation reservation) {
        reservationRepositoryJPA.save(reservation);
    }
    @Override
    public void deleteReservation(Reservation reservation) {
        reservationRepositoryJPA.findById(reservation.getId()).ifPresent(reservationRepositoryJPA::delete);
    }

}
