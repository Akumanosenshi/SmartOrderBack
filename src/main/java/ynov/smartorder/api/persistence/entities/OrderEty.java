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

    @ManyToMany
    @JoinTable(
            name = "t_orders_meals",
            joinColumns = @JoinColumn(name = "order_ety_id"),
            inverseJoinColumns = @JoinColumn(name = "meals_id")
    )
    private List<MealEty> meals;

    @Column(name = "VALIDATED")
    private Boolean validated;

    @Column(name = "TOTAL")
    private Double total;

}
