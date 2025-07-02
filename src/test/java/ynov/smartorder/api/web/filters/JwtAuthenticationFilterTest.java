package ynov.smartorder.api.web.filters;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ynov.smartorder.api.web.services.JwtService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ynov.smartorder.api.web.services.JwtService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import ynov.smartorder.api.web.services.JwtService;

import javax.servlet.FilterChain;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private JwtService jwtService;

    @Mock
    private FilterChain filterChain;

    @Captor
    private ArgumentCaptor<String> stringCaptor;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldBypassAuthForAuthPath() throws Exception {
        // given
        request.setServletPath("/auth/login");

        // when
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // then
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldRejectIfNoAuthorizationHeader() throws Exception {
        // given
        request.setServletPath("/protected");

        // when
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // then
        assert response.getStatus() == 401;
        assert response.getContentAsString().contains("Missing or invalid Authorization header");
    }

    @Test
    void shouldRejectIfTokenInvalid() throws Exception {
        // given
        request.setServletPath("/protected");
        request.addHeader("Authorization", "Bearer invalid.token.here");
        when(jwtService.validateToken("invalid.token.here")).thenReturn(false);

        // when
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // then
        assert response.getStatus() == 401;
        assert response.getContentAsString().contains("Invalid JWT token");
    }

    @Test
    void shouldAuthenticateWithValidToken() throws Exception {
        // given
        request.setServletPath("/protected");
        request.addHeader("Authorization", "Bearer valid.token");
        when(jwtService.validateToken("valid.token")).thenReturn(true);
        when(jwtService.extractEmail("valid.token")).thenReturn("user@mail.com");
        when(jwtService.extractRole("valid.token")).thenReturn("USER");

        // when
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // then
        assert SecurityContextHolder.getContext().getAuthentication() != null;
        assert SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"));
        verify(filterChain).doFilter(request, response);
    }
}


