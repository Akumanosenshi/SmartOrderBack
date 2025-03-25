package ynov.smartorder.api.persistence.entities;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "T_RESERVATIONS")
public class ReservationEty {
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
