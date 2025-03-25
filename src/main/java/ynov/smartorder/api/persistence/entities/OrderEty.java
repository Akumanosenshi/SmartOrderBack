package ynov.smartorder.api.persistence.entities;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "T_ORDERS")
public class OrderEty {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private UUID id;

    @Column(name = "DATE")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserEty user;

    @OneToMany
    @Column(name = "MEALS")
    private List<MealEty> meals;

    @Column(name = "VALIDATED")
    private Boolean validated;

    @Column(name = "TOTAL")
    private Double total;

}
