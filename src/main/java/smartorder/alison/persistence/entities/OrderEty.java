package smartorder.alison.persistence.entities;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "T_ORDERS")
public class OrderEty {
    @javax.persistence.Id
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
