package ynov.smartorder.api.persistence.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ynov.smartorder.api.common.utils.WithRandom;
import ynov.smartorder.api.domain.models.Reservation;
import ynov.smartorder.api.persistence.entities.ReservationEty;
import ynov.smartorder.api.persistence.entities.UserEty;
import ynov.smartorder.api.persistence.mappers.ReservationEtyMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationRepositoryTest implements WithRandom {

    @Mock
    private ReservationRepositoryJPA reservationRepositoryJPA;
    @Mock private UserRepositoryJPA userRepositoryJPA;
    @Mock private ReservationEtyMapper reservationEtyMapper;

    @InjectMocks
    private ReservationRepository reservationRepository;

    @Test
    void shouldSaveReservationIfUserExists() {
        // given
        Reservation reservation = random(Reservation.class);
        UserEty userEty = random(UserEty.class);
        ReservationEty reservationEty = random(ReservationEty.class);
        when(userRepositoryJPA.findById(reservation.getUserId())).thenReturn(Optional.of(userEty));
        when(reservationEtyMapper.toEty(reservation)).thenReturn(reservationEty);

        // when
        reservationRepository.saveReservation(reservation);

        // then
        verify(reservationRepositoryJPA).save(reservationEty);
        assertFalse(reservation.getValidated());
    }

    @Test
    void shouldNotSaveReservationIfUserDoesNotExist() {
        // given
        Reservation reservation = random(Reservation.class);
        when(userRepositoryJPA.findById(reservation.getUserId())).thenReturn(Optional.empty());

        // when
        reservationRepository.saveReservation(reservation);

        // then
        verify(reservationRepositoryJPA, never()).save(any());
    }

    @Test
    void shouldDeleteReservationByIdIfExists() {
        // given
        UUID id = randomUUID();
        ReservationEty reservationEty = random(ReservationEty.class);
        when(reservationRepositoryJPA.findById(id)).thenReturn(Optional.of(reservationEty));

        // when
        reservationRepository.deleteReservation(id);

        // then
        verify(reservationRepositoryJPA).delete(reservationEty);
    }

    @Test
    void shouldFindReservationsByUserId() {
        // given
        UUID userId = randomUUID();
        List<ReservationEty> etys = List.of(random(ReservationEty.class));
        List<Reservation> models = List.of(random(Reservation.class));
        when(reservationRepositoryJPA.findByUserId(userId)).thenReturn(etys);
        when(reservationEtyMapper.toModel(etys.get(0))).thenReturn(models.get(0));

        // when
        List<Reservation> result = reservationRepository.FindReservation(userId);

        // then
        assertEquals(models, result);
    }

    @Test
    void shouldFindAllReservations() {
        // given
        List<ReservationEty> etys = List.of(random(ReservationEty.class));
        List<Reservation> models = List.of(random(Reservation.class));
        when(reservationRepositoryJPA.findAll()).thenReturn(etys);
        when(reservationEtyMapper.toModel(etys.get(0))).thenReturn(models.get(0));

        // when
        List<Reservation> result = reservationRepository.FindAllReservation();

        // then
        assertEquals(models, result);
    }

    @Test
    void shouldReturnTotalReservationsInPeriod() {
        // given
        LocalDateTime start = LocalDateTime.now().minusDays(7);
        LocalDateTime end = LocalDateTime.now();
        ReservationEty inRange = new ReservationEty();
        inRange.setDate(start.plusDays(1));
        ReservationEty outRange = new ReservationEty();
        outRange.setDate(start.minusDays(1));
        when(reservationRepositoryJPA.findAll()).thenReturn(List.of(inRange, outRange));

        // when
        int count = reservationRepository.getTotalReservations(start, end);

        // then
        assertEquals(1, count);
    }

    @Test
    void shouldReturnAveragePeoplePerReservation() {
        // given
        LocalDateTime start = LocalDateTime.now().minusDays(7);
        LocalDateTime end = LocalDateTime.now();
        ReservationEty res1 = new ReservationEty();
        res1.setDate(start.plusDays(1));
        res1.setNbrPeople(4);
        ReservationEty res2 = new ReservationEty();
        res2.setDate(start.plusDays(2));
        res2.setNbrPeople(2);
        when(reservationRepositoryJPA.findAll()).thenReturn(List.of(res1, res2));

        // when
        int avg = reservationRepository.getAveragePeoplePerReservation(start, end);

        // then
        assertEquals(3, avg);
    }

    @Test
    void shouldReturnZeroWhenNoReservationsInAverageCalculation() {
        // given
        when(reservationRepositoryJPA.findAll()).thenReturn(List.of());

        // when
        int avg = reservationRepository.getAveragePeoplePerReservation(LocalDateTime.now().minusDays(1), LocalDateTime.now());

        // then
        assertEquals(0, avg);
    }
}
