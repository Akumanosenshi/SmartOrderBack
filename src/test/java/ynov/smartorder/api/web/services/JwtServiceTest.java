package ynov.smartorder.api.web.services;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    private final String secretKey = "my-super-secret-key-1234567890!@#$%&*1234567890"; // 48+ caractères

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();

        // Génère une clé valide automatiquement (512 bits)
        String secureKey = io.jsonwebtoken.io.Encoders.BASE64.encode(
                io.jsonwebtoken.security.Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded()
        );

        ReflectionTestUtils.setField(jwtService, "secret", secureKey);
    }

    @Test
    void shouldGenerateAndValidateToken() {
        // given
        String email = "test@example.com";
        String role = "USER";

        // when
        String token = jwtService.generateToken(email, role);
        boolean isValid = jwtService.validateToken(token);

        // then
        assertNotNull(token);
        assertTrue(isValid);
    }

    @Test
    void shouldExtractEmailFromToken() {
        // given
        String email = "test@example.com";
        String role = "USER";
        String token = jwtService.generateToken(email, role);

        // when
        String extractedEmail = jwtService.extractEmail(token);

        // then
        assertEquals(email, extractedEmail);
    }

    @Test
    void shouldExtractRoleFromToken() {
        // given
        String email = "test@example.com";
        String role = "RESTAURANT";
        String token = jwtService.generateToken(email, role);

        // when
        String extractedRole = jwtService.extractRole(token);

        // then
        assertEquals(role, extractedRole);
    }

    @Test
    void shouldFailValidationWithInvalidToken() {
        // given
        String invalidToken = "this.is.not.a.valid.token";

        // when
        boolean result = jwtService.validateToken(invalidToken);

        // then
        assertFalse(result);
    }
}
