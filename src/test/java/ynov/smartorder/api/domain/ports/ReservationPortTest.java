package ynov.smartorder.api.domain.ports;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ynov.smartorder.api.common.utils.WithRandom;
import ynov.smartorder.api.domain.models.Reservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationPortTest implements WithRandom {

    @Mock
    private ReservationPort reservationPort;

    @Test
    void shouldSaveReservation() {
        // given
        Reservation reservation = random(Reservation.class);

        // when
        reservationPort.saveReservation(reservation);

        // then
        verify(reservationPort).saveReservation(reservation);
    }

    @Test
    void shouldDeleteReservationById() {
        // given
        UUID id = randomUUID();

        // when
        reservationPort.deleteReservation(id);

        // then
        verify(reservationPort).deleteReservation(id);
    }

    @Test
    void shouldFindReservationByUserId() {
        // given
        UUID userId = randomUUID();
        List<Reservation> reservations = List.of(random(Reservation.class));
        when(reservationPort.FindReservation(userId)).thenReturn(reservations);

        // when
        List<Reservation> result = reservationPort.FindReservation(userId);

        // then
        assertEquals(reservations, result);
    }

    @Test
    void shouldFindAllReservations() {
        // given
        List<Reservation> reservations = List.of(random(Reservation.class), random(Reservation.class));
        when(reservationPort.FindAllReservation()).thenReturn(reservations);

        // when
        List<Reservation> result = reservationPort.FindAllReservation();

        // then
        assertEquals(reservations, result);
    }

    @Test
    void shouldReturnTotalReservationsInPeriod() {
        // given
        LocalDateTime start = LocalDateTime.now().minusDays(10);
        LocalDateTime end = LocalDateTime.now();
        int expected = randomInt();
        when(reservationPort.getTotalReservations(start, end)).thenReturn(expected);

        // when
        int result = reservationPort.getTotalReservations(start, end);

        // then
        assertEquals(expected, result);
    }

    @Test
    void shouldReturnAveragePeoplePerReservation() {
        // given
        LocalDateTime start = LocalDateTime.now().minusDays(10);
        LocalDateTime end = LocalDateTime.now();
        int average = randomInt();
        when(reservationPort.getAveragePeoplePerReservation(start, end)).thenReturn(average);

        // when
        int result = reservationPort.getAveragePeoplePerReservation(start, end);

        // then
        assertEquals(average, result);
    }
}

