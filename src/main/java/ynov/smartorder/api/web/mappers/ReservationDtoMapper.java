package ynov.smartorder.api.web.mappers;

import org.mapstruct.Mapper;
import ynov.smartorder.api.domain.models.Reservation;
import ynov.smartorder.api.web.dtos.ReservationDto;

@Mapper(componentModel = "spring", uses = DateDtoMapper.class)
public interface ReservationDtoMapper {

    ReservationDto toDto(Reservation reservation);

    Reservation toEntity(ReservationDto reservationDto);
}
