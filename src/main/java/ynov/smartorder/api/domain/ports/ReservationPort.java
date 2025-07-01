package ynov.smartorder.api.domain.ports;


import ynov.smartorder.api.domain.models.Reservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ReservationPort {
    void saveReservation(Reservation reservation);

    void deleteReservation(UUID uuid);

    List<Reservation> FindReservation(UUID Id);

    List<Reservation> FindAllReservation();

    int getTotalReservations(LocalDateTime start, LocalDateTime end);

    int getAveragePeoplePerReservation(LocalDateTime start, LocalDateTime end);

}
