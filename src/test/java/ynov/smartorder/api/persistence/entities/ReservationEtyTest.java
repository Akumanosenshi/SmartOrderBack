package ynov.smartorder.api.persistence.entities;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ynov.smartorder.api.common.utils.WithRandom;

import java.time.LocalDateTime;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;



    @ExtendWith(MockitoExtension.class)
    class ReservationEtyTest implements WithRandom {

        @Test
        void shouldSetAndGetReservationEtyFieldsCorrectly() {
            // given
            UUID id = randomUUID();
            LocalDateTime date = LocalDateTime.now();
            Integer nbrPeople = randomInt();
            UUID userId = randomUUID();
            Boolean validated = randomBoolean();

            // when
            ReservationEty reservationEty = new ReservationEty();
            reservationEty.setId(id);
            reservationEty.setDate(date);
            reservationEty.setNbrPeople(nbrPeople);
            reservationEty.setUserId(userId);
            reservationEty.setValidated(validated);

            // then
            assertEquals(id, reservationEty.getId());
            assertEquals(date, reservationEty.getDate());
            assertEquals(nbrPeople, reservationEty.getNbrPeople());
            assertEquals(userId, reservationEty.getUserId());
            assertEquals(validated, reservationEty.getValidated());
        }
    }
