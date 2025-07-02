package ynov.smartorder.api.web.mappers;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ynov.smartorder.api.domain.models.Statistic;
import ynov.smartorder.api.web.dtos.StatisticsDto;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class StatisticDtoMapperTest {

    private final StatisticDtoMapper mapper = Mappers.getMapper(StatisticDtoMapper.class);

    @Test
    void shouldMapStatisticToDto() {
        Statistic stat = new Statistic();
        stat.setTotalOrders(10);
        stat.setTotalReservations(5);
        stat.setTotalRevenue(99.99); // ✅ double, pas BigDecimal
        stat.setAverageCart(9.99);   // ✅ idem
        stat.setAveragePeoplePerReservation((int) 2.5);
        stat.setTopMeals(Collections.emptyList());

        StatisticsDto dto = mapper.toDto(stat);

        assertNotNull(dto);
        assertEquals(stat.getTotalOrders(), dto.getTotalOrders());
        assertEquals(stat.getTotalReservations(), dto.getTotalReservations());
        assertEquals(stat.getTotalRevenue(), dto.getTotalRevenue());
        assertEquals(stat.getAverageCart(), dto.getAverageCart());
        assertEquals(stat.getAveragePeoplePerReservation(), dto.getAveragePeoplePerReservation());
        assertNotNull(dto.getTopMeals());
    }

    @Test
    void shouldMapDtoToStatistic() {
        StatisticsDto dto = new StatisticsDto();
        dto.setTotalOrders(20);
        dto.setTotalReservations(8);
        dto.setTotalRevenue(200.5); // ✅ Double
        dto.setAverageCart(20.05);
        dto.setAveragePeoplePerReservation(3);
        dto.setTopMeals(Collections.emptyList());

        Statistic stat = mapper.toEntity(dto);

        assertNotNull(stat);
        assertEquals(dto.getTotalOrders().intValue(), stat.getTotalOrders());
        assertEquals(dto.getTotalReservations().intValue(), stat.getTotalReservations());
        assertEquals(dto.getTotalRevenue(), stat.getTotalRevenue());
        assertEquals(dto.getAverageCart(), stat.getAverageCart());
        assertEquals(dto.getAveragePeoplePerReservation().intValue(), stat.getAveragePeoplePerReservation());
        assertNotNull(stat.getTopMeals());
    }

}
