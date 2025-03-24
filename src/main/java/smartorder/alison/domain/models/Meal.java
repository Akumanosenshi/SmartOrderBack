package smartorder.alison.domain.models;


import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class Meal {

    private UUID id;

    private String title;
    private String category;
    private String description;
    private String image;

}
