package ynov.smartorder.api.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ynov.smartorder.api.domain.models.Restaurant;
import ynov.smartorder.api.domain.models.User;
import ynov.smartorder.api.domain.ports.RestaurantPort;
import ynov.smartorder.api.domain.ports.UserPort;
import ynov.smartorder.api.web.apis.AuthApi;
import ynov.smartorder.api.web.dtos.*;
import ynov.smartorder.api.web.mappers.UserDtoMapper;
import ynov.smartorder.api.web.services.JwtService;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin(value = "*")
public class AuthController implements AuthApi {


    private final UserPort userPort;
    private final RestaurantPort restaurantPort;
    private final UserDtoMapper userDtoMapper;
    private final JwtService jwtService;


    @Override
    public ResponseEntity<AuthResponseDto> authRegisterPost(@RequestBody UserDto userDto) {
        // Vérifier si l'utilisateur existe déjà
        Optional<User> existingUser = userPort.findUserByEmail(userDto.getEmail());
        if (existingUser.isPresent()) {
            // 409 Conflict
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        // Créer et enregistrer l'utilisateur
        User user = userDtoMapper.toEntity(userDto);
        user.setRole(String.valueOf(RoleDto.USER));
        userPort.saveUser(user);

        String token = jwtService.generateToken(user.getEmail(), "USER");
        AuthResponseDto response = new AuthResponseDto().token(token).role(RoleDto.USER);
        return ResponseEntity.ok(response);
    }


    @Override
    public ResponseEntity<AuthResponseDto> authLoginPost(@RequestBody AuthLoginPostRequestDto authLoginPostRequestDto) {
        Restaurant restaurant = restaurantPort.findRestaurant(authLoginPostRequestDto.getEmail());
        if (restaurant != null && restaurant.getMdp().equals(authLoginPostRequestDto.getMotDePasse())) {
            String token = jwtService.generateToken(restaurant.getEmail(), "RESTAURANT");
            UserPublicDto userPublicDto = new UserPublicDto()
                    .id(restaurant.getId())
                    .email(restaurant.getEmail())
                    .firstname(restaurant.getFirstname())
                    .lastname(restaurant.getLastname())
                    .role(String.valueOf(RoleDto.RESTAURANT));
            return ResponseEntity.ok(new AuthResponseDto().token(token).role(RoleDto.RESTAURANT).user(userPublicDto));
        }
        User user = userPort.findUser(authLoginPostRequestDto.getEmail(), authLoginPostRequestDto.getMotDePasse());

        if (user != null) {
            String token = jwtService.generateToken(user.getEmail(), "USER");
            UserPublicDto userPublicDto = new UserPublicDto()
                    .id(user.getId())
                    .email(user.getEmail())
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .role(String.valueOf(RoleDto.USER));
            return ResponseEntity.ok(new AuthResponseDto().token(token).role(RoleDto.USER).user(userPublicDto));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


}