package ynov.smartorder.api.web.mappers;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ynov.smartorder.api.domain.models.User;
import ynov.smartorder.api.web.dtos.UserPublicDto;
import ynov.smartorder.api.web.mappers.UserPublicDtoMapper;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserPublicDtoMapperTest {

    private final UserPublicDtoMapper mapper = Mappers.getMapper(UserPublicDtoMapper.class);

    @Test
    void testToPublicDto() {
        User user = new User();
        user.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
        user.setFirstname("Alice");
        user.setLastname("Dupont");
        user.setEmail("alice@example.com");
        user.setRole("USER");

        UserPublicDto dto = mapper.toPublicDto(user);

        assertNotNull(dto);
        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getFirstname(), dto.getFirstname());
        assertEquals(user.getLastname(), dto.getLastname());
        assertEquals(user.getEmail(), dto.getEmail());
        assertEquals(user.getRole(), dto.getRole());
    }

    @Test
    void testToUserDto() {
        UserPublicDto dto = new UserPublicDto();
        dto.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
        dto.setFirstname("Alice");
        dto.setLastname("Dupont");
        dto.setEmail("alice@example.com");
        dto.setRole("USER");

        User user = mapper.toUserDto(dto);

        assertNotNull(user);
        assertEquals(dto.getId(), user.getId());
        assertEquals(dto.getFirstname(), user.getFirstname());
        assertEquals(dto.getLastname(), user.getLastname());
        assertEquals(dto.getEmail(), user.getEmail());
        assertEquals(dto.getRole(), user.getRole());
    }
}
