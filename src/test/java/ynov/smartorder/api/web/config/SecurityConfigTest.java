package ynov.smartorder.api.web.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import ynov.smartorder.api.web.config.SecurityConfig;
import ynov.smartorder.api.web.filters.JwtAuthenticationFilter;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class SecurityConfigTest {

    private final JwtAuthenticationFilter jwtAuthenticationFilter = mock(JwtAuthenticationFilter.class);
    private final SecurityConfig securityConfig = new SecurityConfig(jwtAuthenticationFilter);

    @Test
    void shouldProvidePasswordEncoder() {
        // when
        PasswordEncoder encoder = securityConfig.passwordEncoder();

        // then
        assertNotNull(encoder);
        String rawPassword = "testPassword";
        assertTrue(encoder.matches(rawPassword, encoder.encode(rawPassword)));
    }

    @Test
    void shouldConfigureCorsSource() {
        // when
        CorsConfigurationSource source = securityConfig.corsConfigurationSource();

        // then
        assertNotNull(source);
        CorsConfiguration config = source.getCorsConfiguration(new MockHttpServletRequest());
        assertNotNull(config);
        assertTrue(Objects.requireNonNull(config.getAllowedOriginPatterns()).contains("http://localhost:8100"));
        assertNotNull(config.getAllowedMethods());
        assertTrue(config.getAllowedMethods().containsAll(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")));
        assertNotNull(config.getAllowedHeaders());
        assertTrue(config.getAllowedHeaders().contains("*"));
        assertEquals(Boolean.TRUE, config.getAllowCredentials());
    }
}
