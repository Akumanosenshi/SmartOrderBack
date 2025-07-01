package ynov.smartorder.api.web.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ynov.smartorder.api.web.filters.JwtAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .authorizeRequests()

                // 🔓 Accès public sans authentification
                .antMatchers("/auth/**").permitAll()

                // 🟢 Accessible à USER et RESTAURANT
                .antMatchers(HttpMethod.GET, "/Meal", "/Meal/categories", "/Meal/all").hasAnyRole("USER", "RESTAURANT")
                .antMatchers(HttpMethod.POST, "/reservations").hasAnyRole("USER", "RESTAURANT")
                .antMatchers(HttpMethod.GET, "/reservations/user").hasAnyRole("USER", "RESTAURANT")
                .antMatchers(HttpMethod.POST, "/Order").hasAnyRole("USER", "RESTAURANT")
                .antMatchers(HttpMethod.GET, "/Order/user").hasAnyRole("USER", "RESTAURANT")

                // 🔐 RESTAURANT uniquement
                .antMatchers(HttpMethod.POST, "/Meal").hasRole("RESTAURANT")
                .antMatchers(HttpMethod.PUT, "/Meal").hasRole("RESTAURANT")
                .antMatchers(HttpMethod.DELETE, "/Meal").hasRole("RESTAURANT")
                .antMatchers(HttpMethod.GET, "/statistics").hasRole("RESTAURANT")
                .antMatchers(HttpMethod.DELETE, "/reservations").hasRole("RESTAURANT")
                .antMatchers(HttpMethod.GET, "/reservations/all").hasRole("RESTAURANT")
                .antMatchers(HttpMethod.GET, "/Order/all").hasRole("RESTAURANT")
                .antMatchers(HttpMethod.PUT, "/Order/changeState").hasRole("RESTAURANT")

                // Tout le reste nécessite juste d’être connecté
                .anyRequest().authenticated()

                .and()
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("http://localhost:8100")); // Frontend
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true); // autorise le cookie/token

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
