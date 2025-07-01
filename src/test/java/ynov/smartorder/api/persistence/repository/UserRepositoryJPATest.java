package ynov.smartorder.api.persistence.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ynov.smartorder.api.common.utils.WithRandom;
import ynov.smartorder.api.persistence.entities.UserEty;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRepositoryJPATest implements WithRandom {

    @Mock
    private UserRepositoryJPA userRepositoryJPA;

    @Test
    void shouldFindUserByEmail() {
        // given
        String email = randomString();
        UserEty expectedUser = random(UserEty.class);
        when(userRepositoryJPA.findByEmail(email)).thenReturn(Optional.of(expectedUser));

        // when
        Optional<UserEty> result = userRepositoryJPA.findByEmail(email);

        // then
        assertTrue(result.isPresent());
        assertEquals(expectedUser, result.get());
    }
}
