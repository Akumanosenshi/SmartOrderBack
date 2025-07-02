package ynov.smartorder.api.web.mappers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BigDecimalDtoMapperTest {

    private final BigDecimalDtoMapper mapper = new BigDecimalDtoMapper() {};

    @Test
    void shouldConvertDoubleToBigDecimal() {
        // given
        Double value = 12.34;

        // when
        BigDecimal result = mapper.toBigDecimal(value);

        // then
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(12.34), result);
    }

    @Test
    void shouldReturnNullWhenDoubleIsNull() {
        // given
        Double value = null;

        // when
        BigDecimal result = mapper.toBigDecimal(value);

        // then
        assertNull(result);
    }

    @Test
    void shouldConvertBigDecimalToDouble() {
        // given
        BigDecimal value = new BigDecimal("56.78");

        // when
        Double result = mapper.fromBigDecimal(value);

        // then
        assertNotNull(result);
        assertEquals(56.78, result);
    }

    @Test
    void shouldReturnNullWhenBigDecimalIsNull() {
        // given
        BigDecimal value = null;

        // when
        Double result = mapper.fromBigDecimal(value);

        // then
        assertNull(result);
    }
}

