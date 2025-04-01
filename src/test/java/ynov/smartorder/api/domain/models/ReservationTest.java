package ynov.smartorder.api.domain.models;

import org.junit.jupiter.api.Test;
import ynov.smartorder.api.common.utils.WithRandom;

import java.time.LocalDateTime;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
public class ReservationTest implements WithRandom {

@Test
void testGetterSetter() {
    //given
    Reservation reservation = new Reservation();
    UUID id = randomUUID();
    LocalDateTime date = LocalDateTime.now();
    Integer nbrPeople = randomInt();
    User user = random(User.class);
    Boolean validated = randomBoolean();
    //when
    reservation.setId(id);
    reservation.setDate(date);
    reservation.setNbrPeople(nbrPeople);
    reservation.setUser(user);
    reservation.setValidated(validated);
    //then
    assertThat(reservation).isNotNull();
    assertThat(reservation.getId()).isEqualTo(id);
    assertThat(reservation.getDate()).isEqualTo(date);
    assertThat(reservation.getNbrPeople()).isEqualTo(nbrPeople);
    assertThat(reservation.getUser()).isEqualTo(user);
    assertThat(reservation.getValidated()).isEqualTo(validated);
}
}
