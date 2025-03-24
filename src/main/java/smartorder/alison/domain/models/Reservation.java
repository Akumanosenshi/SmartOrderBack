package smartorder.alison.domain.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class Reservation {
    private UUID id;
    private LocalDateTime date;
    private Integer nbrPeople;
    private User user;

}
