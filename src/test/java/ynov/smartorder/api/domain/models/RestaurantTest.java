package ynov.smartorder.api.domain.models;

import org.junit.jupiter.api.Test;
import ynov.smartorder.api.common.utils.WithRandom;
import static org.assertj.core.api.Assertions.assertThat;
public class RestaurantTest implements WithRandom {

    @Test
    void testGetterSetter() {
        //given
        Restaurant restaurant = new Restaurant();
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
