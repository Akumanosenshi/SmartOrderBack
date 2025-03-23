package smartorder.alison.domain.models;


import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class Meal {

    private UUID id;

    private String titre;
    private String categorie;
    private String imageUrl;  // URL de l'image JPG

}
