package ynov.smartorder.api.persistence.mappers;

import org.mapstruct.Mapper;
import ynov.smartorder.api.domain.models.Reservation;
import ynov.smartorder.api.persistence.entities.ReservationEty;

@Mapper(componentModel = "spring", uses = DateTimeEtyMapper.class)
public interface ReservationEtyMapper {

     ReservationEty toEty(final Reservation reservation);

     Reservation toModel(final ReservationEty reservationEty);
}
