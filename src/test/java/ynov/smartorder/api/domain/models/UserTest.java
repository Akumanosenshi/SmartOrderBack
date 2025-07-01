package ynov.smartorder.api.domain.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ynov.smartorder.api.common.utils.WithRandom;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserTest implements WithRandom {

    @Test
    void shouldSetAndGetUserFieldsCorrectly() {
        // given
        UUID id = randomUUID();
        String firstname = randomString();
        String lastname = randomString();
        String email = randomString();
        String mdp = randomString();
        String role = randomString();

        // when
        User user = new User();
        user.setId(id);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setMdp(mdp);
        user.setRole(role);

        // then
        assertEquals(id, user.getId());
        assertEquals(firstname, user.getFirstname());
        assertEquals(lastname, user.getLastname());
        assertEquals(email, user.getEmail());
        assertEquals(mdp, user.getMdp());
        assertEquals(role, user.getRole());
    }
}

