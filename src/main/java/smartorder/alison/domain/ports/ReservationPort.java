package smartorder.alison.domain.ports;

import smartorder.alison.domain.models.Reservation;

public interface ReservationPort {
    void saveReservation(Reservation reservation);

    void deleteReservation(Reservation reservation);
}
