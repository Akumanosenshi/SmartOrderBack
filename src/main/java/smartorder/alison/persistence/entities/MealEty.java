package smartorder.alison.persistence.entities;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "T_MEALS")
public class MealEty {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private UUID id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "DESCRITPION")
    private String description;

    @Column(name = "IMAGE", columnDefinition = "TEXT")
    private String image;

}
