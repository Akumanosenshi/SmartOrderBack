package ynov.smartorder.api.web.mappers;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ynov.smartorder.api.domain.models.Reservation;
import ynov.smartorder.api.web.dtos.ReservationDto;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ReservationDtoMapperTest {

    private final ReservationDtoMapper mapper = Mappers.getMapper(ReservationDtoMapper.class);

    @Test
    void shouldMapReservationToDto() {
        Reservation reservation = new Reservation();
        reservation.setId(UUID.randomUUID());
        reservation.setUserId(UUID.randomUUID());
        reservation.setNbrPeople(4);
        reservation.setDate(LocalDateTime.of(2025, 7, 1, 18, 30));

        ReservationDto dto = mapper.toDto(reservation);

        assertNotNull(dto);
        assertEquals(reservation.getId(), dto.getId());
        assertEquals(reservation.getUserId(), dto.getUserId());
        assertEquals(reservation.getNbrPeople(), dto.getNbrPeople());
        assertEquals(reservation.getDate(), dto.getDate().toLocalDateTime());
    }

    @Test
    void shouldMapDtoToReservation() {
        ReservationDto dto = new ReservationDto();
        dto.setId(UUID.randomUUID());
        dto.setUserId(UUID.randomUUID());
        dto.setNbrPeople(2);
        dto.setDate(OffsetDateTime.of(2025, 7, 1, 20, 0, 0, 0, ZoneOffset.UTC));

        Reservation reservation = mapper.toEntity(dto);

        assertNotNull(reservation);
        assertEquals(dto.getId(), reservation.getId());
        assertEquals(dto.getUserId(), reservation.getUserId());
        assertEquals(dto.getNbrPeople(), reservation.getNbrPeople());
        assertEquals(dto.getDate().toLocalDateTime(), reservation.getDate());
    }
}
