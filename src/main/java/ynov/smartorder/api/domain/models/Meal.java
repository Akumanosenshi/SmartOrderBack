package ynov.smartorder.api.domain.models;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;


@Getter
@Setter
public class Meal {

    private UUID id;

    private String title;
    private String category;
    private String description;
    private String emoji;
    private BigDecimal price;

}
