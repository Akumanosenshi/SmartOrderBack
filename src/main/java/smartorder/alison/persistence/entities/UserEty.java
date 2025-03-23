package smartorder.alison.persistence.entities;


import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Entity
@Getter
@Setter
public class UserEty {

    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private String firstname;
    private String lastname;
    private String email;
    private String mdp;
    private String role ;


}