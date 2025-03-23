package smartorder.alison.persistence.mappers;

import org.mapstruct.Mapper;
import smartorder.alison.domain.models.Reservation;
import smartorder.alison.persistence.entities.ReservationEty;

@Mapper
public interface ReservationEtyMapper {

     ReservationEty toEty(final Reservation reservation);

     Reservation toModel(final ReservationEty reservationEty);
}
