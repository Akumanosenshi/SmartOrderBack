package ynov.smartorder.api.domain.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ynov.smartorder.api.common.utils.WithRandom;

import java.time.LocalDateTime;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ReservationTest implements WithRandom {

    @Test
    void shouldCreateReservationWithCorrectValues() {
        // given
        UUID id = randomUUID();
        LocalDateTime date = LocalDateTime.now();
        Integer nbrPeople = randomInt();
        UUID userId = randomUUID();
        Boolean validated = randomBoolean();

        // when
        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setDate(date);
        reservation.setNbrPeople(nbrPeople);
        reservation.setUserId(userId);
        reservation.setValidated(validated);

        // then
        assertEquals(id, reservation.getId());
        assertEquals(date, reservation.getDate());
        assertEquals(nbrPeople, reservation.getNbrPeople());
        assertEquals(userId, reservation.getUserId());
        assertEquals(validated, reservation.getValidated());
    }
}

