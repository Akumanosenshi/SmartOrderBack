package ynov.smartorder.api.persistence.mappers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DateTimeEtyMapperTest {

    private final DateTimeEtyMapper mapper = new DateTimeEtyMapper() {};

    @Test
    void shouldConvertLocalDateTimeToOffsetDateTime() {
        // given
        LocalDateTime localDateTime = LocalDateTime.now();

        // when
        OffsetDateTime result = mapper.toOffsetDateTime(localDateTime);

        // then
        assertNotNull(result);
        assertEquals(localDateTime, result.toLocalDateTime());
        assertEquals(ZoneOffset.UTC, result.getOffset());
    }

    @Test
    void shouldReturnNullWhenConvertingNullLocalDateTime() {
        // given
        LocalDateTime input = null;

        // when
        OffsetDateTime result = mapper.toOffsetDateTime(input);

        // then
        assertNull(result);
    }

    @Test
    void shouldConvertOffsetDateTimeToLocalDateTime() {
        // given
        OffsetDateTime offsetDateTime = OffsetDateTime.now(ZoneOffset.UTC);

        // when
        LocalDateTime result = mapper.toLocalDateTime(offsetDateTime);

        // then
        assertNotNull(result);
        assertEquals(offsetDateTime.toLocalDateTime(), result);
    }

    @Test
    void shouldReturnNullWhenConvertingNullOffsetDateTime() {
        // given
        OffsetDateTime input = null;

        // when
        LocalDateTime result = mapper.toLocalDateTime(input);

        // then
        assertNull(result);
    }
}

