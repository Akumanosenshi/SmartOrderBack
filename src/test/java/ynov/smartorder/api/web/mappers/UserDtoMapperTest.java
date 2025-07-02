package ynov.smartorder.api.web.mappers;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ynov.smartorder.api.domain.models.User;
import ynov.smartorder.api.web.dtos.UserDto;
import ynov.smartorder.api.web.mappers.UserDtoMapper;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserDtoMapperTest {

    private final UserDtoMapper mapper = Mappers.getMapper(UserDtoMapper.class);

    @Test
    void shouldMapUserToDto() {
        // given
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setFirstname("Alice");
        user.setLastname("Dupont");
        user.setEmail("alice@example.com");
        user.setMdp("hashed_password");
        user.setRole("USER");

        // when
        UserDto dto = mapper.toDto(user);

        // then
        assertNotNull(dto);
        assertEquals(user.getFirstname(), dto.getFirstname());
        assertEquals(user.getLastname(), dto.getLastname());
        assertEquals(user.getEmail(), dto.getEmail());
        assertEquals(user.getMdp(), dto.getMdp());
    }

    @Test
    void shouldMapDtoToUser() {
        // given
        UserDto dto = new UserDto();
        dto.setFirstname("Bob");
        dto.setLastname("Martin");
        dto.setEmail("bob@example.com");
        dto.setMdp("plain_password");

        // when
        User user = mapper.toEntity(dto);

        // then
        assertNotNull(user);
        assertEquals(dto.getFirstname(), user.getFirstname());
        assertEquals(dto.getLastname(), user.getLastname());
        assertEquals(dto.getEmail(), user.getEmail());
        assertEquals(dto.getMdp(), user.getMdp());
    }
}

