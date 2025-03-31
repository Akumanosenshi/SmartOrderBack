package ynov.smartorder.api.persistence.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "T_Restaurant")
public class RestaurantEty {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private UUID id;
    @Column(name = "FIRSTNAME")
    private String firstname;
    @Column(name = "LASTNAME")
    private String lastname;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "MDP")
    private String mdp;
    @Column(name = "ROLE")
    private String role;
}
