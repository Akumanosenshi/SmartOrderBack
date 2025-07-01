package ynov.smartorder.api.domain.ports;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ynov.smartorder.api.common.utils.WithRandom;
import ynov.smartorder.api.domain.models.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserPortTest implements WithRandom {

    @Mock
    private UserPort userPort;

    @Test
    void shouldSaveUser() {
        // given
        User user = random(User.class);

        // when
        userPort.saveUser(user);

        // then
        verify(userPort).saveUser(user);
    }

    @Test
    void shouldFindUserByEmailAndPassword() {
        // given
        String email = randomString();
        String password = randomString();
        User expectedUser = random(User.class);
        when(userPort.findUser(email, password)).thenReturn(expectedUser);

        // when
        User result = userPort.findUser(email, password);

        // then
        assertEquals(expectedUser, result);
    }

    @Test
    void shouldFindUserByEmail() {
        // given
        String email = randomString();
        User user = random(User.class);
        when(userPort.findUserByEmail(email)).thenReturn(Optional.of(user));

        // when
        Optional<User> result = userPort.findUserByEmail(email);

        // then
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }
}

