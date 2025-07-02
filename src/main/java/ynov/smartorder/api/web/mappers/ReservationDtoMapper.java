package ynov.smartorder.api.web.mappers;

import org.mapstruct.*;
import ynov.smartorder.api.domain.models.Reservation;
import ynov.smartorder.api.web.dtos.ReservationDto;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface ReservationDtoMapper {

    @Mappings({
            @Mapping(target = "date", ignore = true)
    })
    ReservationDto toDto(Reservation reservation);

    @AfterMapping
    default void afterToDto(Reservation reservation, @MappingTarget ReservationDto dto) {
        if (reservation != null && reservation.getDate() != null) {
            dto.setDate(reservation.getDate().atOffset(ZoneOffset.UTC));
        }
    }

    @Mappings({
            @Mapping(target = "date", ignore = true)
    })
    Reservation toEntity(ReservationDto dto);

    @AfterMapping
    default void afterToEntity(ReservationDto dto, @MappingTarget Reservation reservation) {
        if (dto != null && dto.getDate() != null) {
            reservation.setDate(dto.getDate().toLocalDateTime());
        }
    }
}
