package ynov.smartorder.api.persistence.entities;

import org.junit.jupiter.api.Test;
import ynov.smartorder.api.common.utils.WithRandom;
import static org.assertj.core.api.Assertions.assertThat;
public class RestaurantEtyTest implements WithRandom {

    @Test
    void testGettersAndSetters() {
        //given
        RestaurantEty restaurant = new RestaurantEty();
       String firstname = randomString();
        String lastname = randomString();
        String email = randomString();
        String mdp = randomString();
        String role = randomString();
        //when
        restaurant.setFirstname(firstname);
        restaurant.setLastname(lastname);
        restaurant.setEmail(email);
        restaurant.setMdp(mdp);
        restaurant.setRole(role);
        //then
        assertThat(restaurant).isNotNull();
        assertThat(restaurant.getFirstname()).isEqualTo(firstname);
        assertThat(restaurant.getLastname()).isEqualTo(lastname);
        assertThat(restaurant.getEmail()).isEqualTo(email);
        assertThat(restaurant.getMdp()).isEqualTo(mdp);
        assertThat(restaurant.getRole()).isEqualTo(role);
    }
}
