package ynov.smartorder.api.persistence.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ynov.smartorder.api.common.utils.WithRandom;
import ynov.smartorder.api.persistence.entities.ReservationEty;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationRepositoryJPATest implements WithRandom {

    @Mock
    private ReservationRepositoryJPA reservationRepositoryJPA;

    @Test
    void shouldFindReservationsByUserId() {
        // given
        UUID userId = randomUUID();
        List<ReservationEty> expected = List.of(random(ReservationEty.class), random(ReservationEty.class));
        when(reservationRepositoryJPA.findByUserId(userId)).thenReturn(expected);

        // when
        List<ReservationEty> result = reservationRepositoryJPA.findByUserId(userId);

        // then
        assertEquals(expected, result);
    }
}
