package smartorder.alison.persistence.entities;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class OrderEty {
    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @ManyToOne
    private UserEty user;

    @OneToMany
    private List<MealEty> meals;

    private double total;

}
