package ynov.smartorder.api.persistence.entities;

import org.junit.jupiter.api.Test;
import ynov.smartorder.api.common.utils.WithRandom;

import java.time.LocalDateTime;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
public class ReservationEtyTest implements WithRandom {

    @Test
    void testGettersAndSetters() {
        //given
        ReservationEty reservation = new ReservationEty();
        UUID id = UUID.randomUUID();
        LocalDateTime date = LocalDateTime.now();
        Integer nbrPeople = randomInt();
        UserEty user = random(UserEty.class);
        Boolean validated = randomBoolean();
        //when
        reservation.setId(id);
        reservation.setDate(date);
        reservation.setNbrPeople(nbrPeople);
        reservation.setValidated(validated);
        //then
        assertThat(reservation).isNotNull();
        assertThat(reservation.getId()).isEqualTo(id);
        assertThat(reservation.getDate()).isEqualTo(date);
        assertThat(reservation.getNbrPeople()).isEqualTo(nbrPeople);
        assertThat(reservation.getValidated()).isEqualTo(validated);

    }
}
