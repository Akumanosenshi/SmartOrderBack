package ynov.smartorder.api.persistence.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ynov.smartorder.api.common.utils.WithRandom;
import ynov.smartorder.api.domain.models.User;
import ynov.smartorder.api.persistence.entities.UserEty;
import ynov.smartorder.api.persistence.mappers.UserEtyMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest implements WithRandom {

    @Mock
    private UserRepositoryJPA userRepositoryJPA;
    @Mock private UserEtyMapper userEtyMapper;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserRepository userRepository;

    @Test
    void shouldSaveUserWithEncodedPassword() {
        // given
        User user = random(User.class);
        String rawPassword = user.getMdp();
        String encodedPassword = "encoded-password";
        UserEty userEty = random(UserEty.class);

        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(userEtyMapper.toEty(user)).thenReturn(userEty);

        // when
        userRepository.saveUser(user);

        // then
        verify(passwordEncoder).encode(rawPassword);
        verify(userRepositoryJPA).save(userEty);
        assertEquals(encodedPassword, user.getMdp());
    }

    @Test
    void shouldReturnUserIfPasswordMatches() {
        // given
        String email = randomString();
        String rawPassword = "plainPass";
        String encodedPassword = "hashedPass";

        UserEty userEty = new UserEty();
        userEty.setEmail(email);
        userEty.setMdp(encodedPassword);

        User mappedUser = new User();
        mappedUser.setEmail(email);
        mappedUser.setMdp(encodedPassword);

        when(userRepositoryJPA.findByEmail(email)).thenReturn(Optional.of(userEty));
        when(userEtyMapper.toModel(userEty)).thenReturn(mappedUser);
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

        // when
        User result = userRepository.findUser(email, rawPassword);

        // then
        assertNotNull(result);
        assertEquals(mappedUser, result);
    }

    @Test
    void shouldReturnNullIfUserNotFound() {
        // given
        String email = randomString();
        String password = randomString();
        when(userRepositoryJPA.findByEmail(email)).thenReturn(Optional.empty());

        // when
        User result = userRepository.findUser(email, password);

        // then
        assertNull(result);
    }

    @Test
    void shouldReturnNullIfPasswordDoesNotMatch() {
        // given
        String email = randomString();
        String rawPassword = "wrongPass";
        String encodedPassword = "hashedPass";

        UserEty userEty = new UserEty();
        userEty.setEmail(email);
        userEty.setMdp(encodedPassword);

        User mappedUser = new User();
        mappedUser.setEmail(email);
        mappedUser.setMdp(encodedPassword);

        when(userRepositoryJPA.findByEmail(email)).thenReturn(Optional.of(userEty));
        when(userEtyMapper.toModel(userEty)).thenReturn(mappedUser);
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false); // simulation du mot de passe incorrect

        // when
        User result = userRepository.findUser(email, rawPassword);

        // then
        assertNull(result);
    }


    @Test
    void shouldFindUserByEmail() {
        // given
        String email = randomString();
        UserEty userEty = random(UserEty.class);
        User user = random(User.class);

        when(userRepositoryJPA.findByEmail(email)).thenReturn(Optional.of(userEty));
        when(userEtyMapper.toModel(userEty)).thenReturn(user);

        // when
        Optional<User> result = userRepository.findUserByEmail(email);

        // then
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }
}
