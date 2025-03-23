package smartorder.alison.persistence.entities;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class ReservationEty {
    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private LocalDateTime date;
    private int nmbrPeople;

    @ManyToOne
    private UserEty user;

}
