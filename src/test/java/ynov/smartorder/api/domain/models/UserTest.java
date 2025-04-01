package ynov.smartorder.api.domain.models;

import org.junit.jupiter.api.Test;
import ynov.smartorder.api.common.utils.WithRandom;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
public class UserTest implements WithRandom {

    @Test
    void testGetterSetter() {
        //given
        User user = new User();
        UUID id = randomUUID();
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
