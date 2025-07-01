package ynov.smartorder.api.persistence.mappers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ynov.smartorder.api.common.utils.WithRandom;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BigDecimalEtyMapperTest implements WithRandom {

    private final BigDecimalEtyMapper mapper = new BigDecimalEtyMapper() {};

    @Test
    void shouldConvertDoubleToBigDecimal() {
        // given
        Double input = randomDouble();

        // when
        BigDecimal result = mapper.toBigDecimal(input);

        // then
        assertNotNull(result);
        assertEquals(input, result.doubleValue(), 0.0001);
    }

    @Test
    void shouldReturnNullWhenConvertingNullDoubleToBigDecimal() {
        // given
        Double input = null;

        // when
        BigDecimal result = mapper.toBigDecimal(input);

        // then
        assertNull(result);
    }

    @Test
    void shouldConvertBigDecimalToDouble() {
        // given
        BigDecimal input = randomBigDecimal();

        // when
        Double result = mapper.fromBigDecimal(input);

        // then
        assertNotNull(result);
        assertEquals(input.doubleValue(), result, 0.0001);
    }

    @Test
    void shouldReturnNullWhenConvertingNullBigDecimalToDouble() {
        // given
        BigDecimal input = null;

        // when
        Double result = mapper.fromBigDecimal(input);

        // then
        assertNull(result);
    }
}

