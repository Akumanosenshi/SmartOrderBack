package smartorder.alison.persistence.entities;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Entity
@Getter
@Setter
public class MealEty {
    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String title;
    private String categorie;
    private String imageUrl;  // URL de l'image JPG

}
