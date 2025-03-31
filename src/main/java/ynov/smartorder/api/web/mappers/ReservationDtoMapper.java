package ynov.smartorder.api.web.mappers;

import org.mapstruct.Mapper;
import smartorder.alison_api.web.dtos.ReservationDto;
import ynov.smartorder.api.domain.models.Reservation;

@Mapper(componentModel = "spring", uses = DateDtoMapper.class)
public interface ReservationDtoMapper {

    ReservationDto toDto(Reservation reservation);

    Reservation toEntity(ReservationDto reservationDto);
}
