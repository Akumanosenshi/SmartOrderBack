package smartorder.alison.domain.models;


import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;


@Getter
@Setter
public class Order {
    private UUID id;

    private User user;

    private List<Meal> meals;

    private double total;

}
