package ynov.smartorder.api.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ynov.smartorder.api.domain.models.User;
import ynov.smartorder.api.domain.ports.UserPort;
import ynov.smartorder.api.web.dtos.*;
import ynov.smartorder.api.web.mappers.UserDtoMapper;
import ynov.smartorder.api.web.mappers.UserPublicDtoMapper;
import ynov.smartorder.api.web.services.BruteForceProtectionService;
import ynov.smartorder.api.web.services.JwtService;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private UserPort userPort;
    @Mock
    private UserDtoMapper userDtoMapper;
    @Mock
    private JwtService jwtService;
    @Mock
    private BruteForceProtectionService bruteForceProtectionService;
    @Mock
    private UserPublicDtoMapper userPublicDtoMapper;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void authRegisterPost_shouldReturnConflictIfUserExists() {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");

        when(userPort.findUserByEmail("test@example.com"))
                .thenReturn(Optional.of(new User()));

        ResponseEntity<AuthResponseDto> response = authController.authRegisterPost(userDto);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void authRegisterPost_shouldRegisterNewUser() {
        UserDto userDto = new UserDto();
        userDto.setEmail("new@example.com");

        User mappedUser = new User();
        mappedUser.setEmail("new@example.com");

        User savedUser = new User();
        savedUser.setEmail("new@example.com");
        savedUser.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
        savedUser.setFirstname("Jean");
        savedUser.setLastname("Dupont");
        savedUser.setRole("USER");

        when(userPort.findUserByEmail("new@example.com")).thenReturn(Optional.empty(), Optional.of(savedUser));
        when(userDtoMapper.toEntity(userDto)).thenReturn(mappedUser);
        when(jwtService.generateToken("new@example.com", "USER")).thenReturn("fake-jwt-token");
        when(userPublicDtoMapper.toPublicDto(savedUser)).thenReturn(new UserPublicDto());

        ResponseEntity<AuthResponseDto> response = authController.authRegisterPost(userDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("fake-jwt-token", response.getBody().getToken());
    }

    @Test
    void authLoginPost_shouldBlockIfUserIsBlocked() {
        AuthLoginPostRequestDto loginDto = new AuthLoginPostRequestDto();
        loginDto.setEmail("blocked@example.com");

        when(bruteForceProtectionService.isBlocked("blocked@example.com")).thenReturn(true);

        ResponseEntity<AuthResponseDto> response = authController.authLoginPost(loginDto);

        assertEquals(HttpStatus.TOO_MANY_REQUESTS, response.getStatusCode());
    }

    @Test
    void authLoginPost_shouldReturnOkIfCredentialsAreCorrect() {
        AuthLoginPostRequestDto loginDto = new AuthLoginPostRequestDto();
        loginDto.setEmail("user@example.com");
        loginDto.setMotDePasse("password");

        User user = new User();
        user.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
        user.setEmail("user@example.com");
        user.setFirstname("Test");
        user.setLastname("User");
        user.setRole("USER");

        when(bruteForceProtectionService.isBlocked("user@example.com")).thenReturn(false);
        when(userPort.findUser("user@example.com", "password")).thenReturn(user);
        when(jwtService.generateToken("user@example.com", "USER")).thenReturn("token");

        ResponseEntity<AuthResponseDto> response = authController.authLoginPost(loginDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("token", response.getBody().getToken());
    }

    @Test
    void authLoginPost_shouldReturnUnauthorizedIfCredentialsAreWrong() {
        AuthLoginPostRequestDto loginDto = new AuthLoginPostRequestDto();
        loginDto.setEmail("fail@example.com");
        loginDto.setMotDePasse("wrong");

        when(bruteForceProtectionService.isBlocked("fail@example.com")).thenReturn(false);
        when(userPort.findUser("fail@example.com", "wrong")).thenReturn(null);

        ResponseEntity<AuthResponseDto> response = authController.authLoginPost(loginDto);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}
