package ynov.smartorder.api.domain.models;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class User {
    private UUID id;
    private String firstname;
    private String lastname;
    private String email;
    private String mdp;
    private String role;

}