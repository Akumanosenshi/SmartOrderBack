package smartorder.alison.web.mappers;
import org.mapstruct.Mapper;
import smartorder.alison.domain.models.Reservation;
import smartorder.alison.web.dtos.ReservationDto;

@Mapper
public interface ReservationDtoMapper {

    ReservationDto toDto(Reservation reservation);

    Reservation toEntity(ReservationDto reservationDto);
}
