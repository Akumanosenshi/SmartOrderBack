package ynov.smartorder.api.web.mappers;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

class DateDtoMapperTest {

    private final DateDtoMapper mapper = new DateDtoMapper() {};

    @Test
    void shouldConvertLocalDateTimeToOffsetDateTime() {
        // given
        LocalDateTime localDateTime = LocalDateTime.of(2023, 7, 1, 12, 30);

        // when
        OffsetDateTime result = mapper.toOffsetDateTime(localDateTime);

        // then
        assertNotNull(result);
        assertEquals(localDateTime.atOffset(ZoneOffset.UTC), result);
    }

    @Test
    void shouldReturnNullWhenLocalDateTimeIsNull() {
        // when
        OffsetDateTime result = mapper.toOffsetDateTime(null);

        // then
        assertNull(result);
    }

    @Test
    void shouldConvertOffsetDateTimeToLocalDateTime() {
        // given
        OffsetDateTime offsetDateTime = OffsetDateTime.of(2023, 7, 1, 15, 45, 0, 0, ZoneOffset.UTC);

        // when
        LocalDateTime result = mapper.toLocalDateTime(offsetDateTime);

        // then
        assertNotNull(result);
        assertEquals(offsetDateTime.toLocalDateTime(), result);
    }

    @Test
    void shouldReturnNullWhenOffsetDateTimeIsNull() {
        // when
        LocalDateTime result = mapper.toLocalDateTime(null);

        // then
        assertNull(result);
    }
}
