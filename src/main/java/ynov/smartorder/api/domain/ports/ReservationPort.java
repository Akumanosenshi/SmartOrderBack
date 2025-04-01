package ynov.smartorder.api.domain.ports;


import ynov.smartorder.api.domain.models.Reservation;

import java.util.List;
import java.util.UUID;

public interface ReservationPort {
    void saveReservation(Reservation reservation);

    void deleteReservation(UUID uuid);

    void updateReservation(Reservation reservation);

    List<Reservation> FindReservation(UUID Id);

    List<Reservation> FindAllReservation();

}
