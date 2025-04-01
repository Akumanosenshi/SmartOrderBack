package ynov.smartorder.api.persistence.entities;

import org.junit.jupiter.api.Test;
import ynov.smartorder.api.common.utils.WithRandom;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
public class UserEtyTest implements WithRandom {

    @Test
    void testGettersAndSetters() {
        //given
        UserEty user = new UserEty();
        UUID id = UUID.randomUUID();
        String firstname = randomString();
        String lastname = randomString();
        String email = randomString();
        String mdp = randomString();
        String role = randomString();
        //when
        user.setId(id);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setMdp(mdp);
        user.setRole(role);
        //then
        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getFirstname()).isEqualTo(firstname);
        assertThat(user.getLastname()).isEqualTo(lastname);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getMdp()).isEqualTo(mdp);
        assertThat(user.getRole()).isEqualTo(role);
    }
}
