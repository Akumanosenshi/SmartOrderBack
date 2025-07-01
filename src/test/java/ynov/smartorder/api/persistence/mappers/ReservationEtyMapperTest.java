package ynov.smartorder.api.persistence.mappers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import ynov.smartorder.api.common.utils.WithRandom;
import ynov.smartorder.api.domain.models.Reservation;
import ynov.smartorder.api.persistence.entities.ReservationEty;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ReservationEtyMapperTest implements WithRandom {

    private final ReservationEtyMapper mapper = Mappers.getMapper(ReservationEtyMapper.class);

    @Test
    void shouldMapReservationToReservationEty() {
        // given
        Reservation reservation = random(Reservation.class);

        // when
        ReservationEty result = mapper.toEty(reservation);

        // then
        assertNotNull(result);
        assertEquals(reservation.getId(), result.getId());
        assertEquals(reservation.getDate(), result.getDate());
        assertEquals(reservation.getNbrPeople(), result.getNbrPeople());
        assertEquals(reservation.getUserId(), result.getUserId());
        assertEquals(reservation.getValidated(), result.getValidated());
    }

    @Test
    void shouldMapReservationEtyToReservation() {
        // given
        ReservationEty reservationEty = random(ReservationEty.class);

        // when
        Reservation result = mapper.toModel(reservationEty);

        // then
        assertNotNull(result);
        assertEquals(reservationEty.getId(), result.getId());
        assertEquals(reservationEty.getDate(), result.getDate());
        assertEquals(reservationEty.getNbrPeople(), result.getNbrPeople());
        assertEquals(reservationEty.getUserId(), result.getUserId());
        assertEquals(reservationEty.getValidated(), result.getValidated());
    }
}
