package ynov.smartorder.api.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import ynov.smartorder.api.domain.models.Reservation;
import ynov.smartorder.api.domain.models.User;
import ynov.smartorder.api.domain.ports.ReservationPort;
import ynov.smartorder.api.persistence.entities.UserEty;
import ynov.smartorder.api.persistence.mappers.ReservationEtyMapper;
import ynov.smartorder.api.persistence.mappers.UserEtyMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ReservationRepository implements ReservationPort {
    private final ReservationRepositoryJPA reservationRepositoryJPA;
    private final UserRepositoryJPA userRepositoryJPA;
    private final UserEtyMapper userEtyMapper;
    private final ReservationEtyMapper reservationEtyMapper;

    @Override
    public void saveReservation(Reservation reservation) {
        Optional<UserEty> user = userRepositoryJPA.findByEmail(reservation.getUser().getEmail());
        if (user.isEmpty()) {
            return;
        }
        reservation.setUser(userEtyMapper.toModel(user.get()));
        reservationRepositoryJPA.save(reservationEtyMapper.toEty(reservation));
    }

    @Override
    public void deleteReservation(UUID uuid) {
        reservationRepositoryJPA.findById(uuid).ifPresent(reservationRepositoryJPA::delete);
    }

    @Override
    public void updateReservation(Reservation reservation) {
        if (reservation.getId() != null) {
            reservationRepositoryJPA.findById(reservation.getId())
                    .ifPresent(existing -> {
                        existing.setDate(reservation.getDate());
                        existing.setNbrPeople(reservation.getNbrPeople());

                        if (reservation.getUser() != null && reservation.getUser().getEmail() != null) {
                            userRepositoryJPA.findByEmail(reservation.getUser().getEmail())
                                    .ifPresent(existing::setUser);
                        }

                        reservationRepositoryJPA.save(existing);
                    });
        }
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
