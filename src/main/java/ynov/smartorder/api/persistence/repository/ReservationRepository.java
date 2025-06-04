package ynov.smartorder.api.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import ynov.smartorder.api.domain.models.Reservation;
import ynov.smartorder.api.domain.models.User;
import ynov.smartorder.api.domain.ports.ReservationPort;
import ynov.smartorder.api.persistence.entities.ReservationEty;
import ynov.smartorder.api.persistence.entities.UserEty;
import ynov.smartorder.api.persistence.mappers.ReservationEtyMapper;
import ynov.smartorder.api.persistence.mappers.UserEtyMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
        Optional<ReservationEty> existingReservation = reservationRepositoryJPA.findById(reservation.getId());
        if (existingReservation.isPresent()) {
            ReservationEty updatedReservation = reservationEtyMapper.toEty(reservation);
            updatedReservation.setUser(existingReservation.get().getUser());
            reservationRepositoryJPA.save(updatedReservation);
        }
    }


    @Override
    public List<Reservation> FindReservation(UUID id) {
        List<ReservationEty> reservations = reservationRepositoryJPA.findByUser_Id(id);
        return reservations.stream().map(reservationEtyMapper::toModel).collect(Collectors.toList());
    }

    @Override
    public List<Reservation> FindAllReservation() {
        return reservationRepositoryJPA.findAll().stream().map(reservationEtyMapper::toModel).collect(Collectors.toList());
    }

    @Override
    public int getTotalReservations(LocalDateTime start, LocalDateTime end) {
        return (int) reservationRepositoryJPA.findAll()
                .stream()
                .filter(reservationEty -> reservationEty.getDate().isAfter(start) && reservationEty.getDate().isBefore(end)).count();
    }

    @Override
    public int getAveragePeoplePerReservation(LocalDateTime start, LocalDateTime end) {
        List<ReservationEty> reservations = reservationRepositoryJPA.findAll()
                .stream()
                .filter(reservationEty -> reservationEty.getDate().isAfter(start) && reservationEty.getDate().isBefore(end))
                .collect(Collectors.toList());

        if (reservations.isEmpty()) {
            return 0;
        }

        int totalPeople = reservations.stream().mapToInt(ReservationEty::getNbrPeople).sum();
        return totalPeople / reservations.size();
    }

}
