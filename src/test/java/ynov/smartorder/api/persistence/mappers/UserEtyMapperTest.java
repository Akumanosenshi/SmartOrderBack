package ynov.smartorder.api.persistence.mappers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import ynov.smartorder.api.common.utils.WithRandom;
import ynov.smartorder.api.domain.models.User;
import ynov.smartorder.api.persistence.entities.UserEty;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class UserEtyMapperTest implements WithRandom {

    private final UserEtyMapper mapper = Mappers.getMapper(UserEtyMapper.class);

    @Test
    void shouldMapUserToUserEty() {
        // given
        User user = random(User.class);

        // when
        UserEty result = mapper.toEty(user);

        // then
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getFirstname(), result.getFirstname());
        assertEquals(user.getLastname(), result.getLastname());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getMdp(), result.getMdp());
        assertEquals(user.getRole(), result.getRole());
    }

    @Test
    void shouldMapUserEtyToUser() {
        // given
        UserEty userEty = random(UserEty.class);

        // when
        User result = mapper.toModel(userEty);

        // then
        assertNotNull(result);
        assertEquals(userEty.getId(), result.getId());
        assertEquals(userEty.getFirstname(), result.getFirstname());
        assertEquals(userEty.getLastname(), result.getLastname());
        assertEquals(userEty.getEmail(), result.getEmail());
        assertEquals(userEty.getMdp(), result.getMdp());
        assertEquals(userEty.getRole(), result.getRole());
    }
}
