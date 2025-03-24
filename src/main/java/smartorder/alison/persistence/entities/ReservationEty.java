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
@Table(name = "T_RESERVATIONS")
public class ReservationEty {
    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private UUID id;

    @Column(name = "DATE")
    private LocalDateTime date;

    @Column(name = "NBR_PEOPLE")
    private Integer nbrPeople;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserEty user;

}
