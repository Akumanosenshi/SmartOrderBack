package ynov.smartorder.api.web.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ynov.smartorder.api.common.utils.WithRandom;
import ynov.smartorder.api.domain.models.User;
import ynov.smartorder.api.domain.ports.UserPort;
import ynov.smartorder.api.web.dtos.AuthLoginPostRequestDto;
import ynov.smartorder.api.web.dtos.AuthResponseDto;
import ynov.smartorder.api.web.dtos.RoleDto;
import ynov.smartorder.api.web.dtos.UserDto;
import ynov.smartorder.api.web.mappers.UserDtoMapper;
import ynov.smartorder.api.web.services.BruteForceProtectionService;
import ynov.smartorder.api.web.services.JwtService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest implements WithRandom {

    @Mock
    private UserPort userPort;
    @Mock private UserDtoMapper userDtoMapper;
    @Mock private JwtService jwtService;
    @Mock private BruteForceProtectionService bruteForceProtectionService;

    @InjectMocks
    private AuthController authController;

    @Test
    void shouldBlockLoginWhenUserIsBlocked() {
        // given
        AuthLoginPostRequestDto requestDto = new AuthLoginPostRequestDto();
        requestDto.setEmail("test@example.com");

        when(bruteForceProtectionService.isBlocked(requestDto.getEmail())).thenReturn(true);

        // when
        ResponseEntity<AuthResponseDto> response = authController.authLoginPost(requestDto);

        // then
        assertEquals(HttpStatus.TOO_MANY_REQUESTS, response.getStatusCode());
        assertNull(response.getBody());
        verifyNoInteractions(userPort);
    }

    @Test
    void shouldLoginSuccessfully() {
        // given
        AuthLoginPostRequestDto request = new AuthLoginPostRequestDto();
        request.setEmail("user@example.com");
        request.setMotDePasse("password");

        User user = random(User.class);
        user.setEmail(request.getEmail());
        user.setRole("USER");

        String token = "jwt-token";

        when(bruteForceProtectionService.isBlocked(request.getEmail())).thenReturn(false);
        when(userPort.findUser(request.getEmail(), request.getMotDePasse())).thenReturn(user);
        when(jwtService.generateToken(user.getEmail(), user.getRole())).thenReturn(token);

        // when
        ResponseEntity<AuthResponseDto> response = authController.authLoginPost(request);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(token, response.getBody().getToken());
        assertEquals(RoleDto.USER, response.getBody().getRole());
        verify(bruteForceProtectionService).resetAttempts(user.getEmail());
    }

    @Test
    void shouldFailLoginAndRecordAttempt() {
        // given
        AuthLoginPostRequestDto request = new AuthLoginPostRequestDto();
        request.setEmail("user@example.com");
        request.setMotDePasse("wrongPass");

        when(bruteForceProtectionService.isBlocked(request.getEmail())).thenReturn(false);
        when(userPort.findUser(request.getEmail(), request.getMotDePasse())).thenReturn(null);

        // when
        ResponseEntity<AuthResponseDto> response = authController.authLoginPost(request);

        // then
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        verify(bruteForceProtectionService).recordFailedAttempt(request.getEmail());
    }

    @Test
    void shouldRegisterUserIfEmailNotTaken() {
        // given
        UserDto userDto = random(UserDto.class);
        User user = random(User.class);

        when(userPort.findUserByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        when(userDtoMapper.toEntity(userDto)).thenReturn(user);
        when(jwtService.generateToken(user.getEmail(), "USER")).thenReturn("token");

        // when
        ResponseEntity<AuthResponseDto> response = authController.authRegisterPost(userDto);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("token", response.getBody().getToken());
        assertEquals(RoleDto.USER, response.getBody().getRole());
        verify(userPort).saveUser(user);
    }

    @Test
    void shouldNotRegisterUserIfEmailAlreadyExists() {
        // given
        UserDto userDto = random(UserDto.class);
        User existingUser = random(User.class);

        when(userPort.findUserByEmail(userDto.getEmail())).thenReturn(Optional.of(existingUser));

        // when
        ResponseEntity<AuthResponseDto> response = authController.authRegisterPost(userDto);

        // then
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        verify(userPort, never()).saveUser(any());
    }
}