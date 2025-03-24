package smartorder.alison.domain.models;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
public class Order {
    private UUID id;

    private LocalDateTime date;

    private User user;

    private List<Meal> meals;

    private Boolean validated;

    private Double total;

}
