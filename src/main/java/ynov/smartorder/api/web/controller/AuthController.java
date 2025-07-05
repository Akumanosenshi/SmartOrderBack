package ynov.smartorder.api.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ynov.smartorder.api.domain.models.User;
import ynov.smartorder.api.domain.ports.UserPort;
import ynov.smartorder.api.web.apis.AuthApi;
import ynov.smartorder.api.web.dtos.*;
import ynov.smartorder.api.web.mappers.UserDtoMapper;
import ynov.smartorder.api.web.mappers.UserPublicDtoMapper;
import ynov.smartorder.api.web.services.BruteForceProtectionService;
import ynov.smartorder.api.web.services.JwtService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin(value = "*")
public class AuthController implements AuthApi {


    private final UserPort userPort;
    private final UserDtoMapper userDtoMapper;
    private final JwtService jwtService;
    private final BruteForceProtectionService bruteForceProtectionService;
    private final UserPublicDtoMapper userPublicDtoMapper;


    @Override
    public ResponseEntity<AuthResponseDto> authRegisterPost(@RequestBody UserDto userDto) {
        // Vérifier si l'utilisateur existe déjà
        Optional<User> existingUser = userPort.findUserByEmail(userDto.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409
        }

        // Mapper l'utilisateur et forcer le rôle USER
        User user = userDtoMapper.toEntity(userDto);
        user.setRole(String.valueOf(RoleDto.USER));

        // Enregistrement de l'utilisateur
        userPort.saveUser(user);

        // Récupérer l'utilisateur sauvegardé depuis la BDD
        Optional<User> savedUserOpt = userPort.findUserByEmail(user.getEmail());
        if (savedUserOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        User savedUser = savedUserOpt.get();

        // Génération du token
        String token = jwtService.generateToken(savedUser.getEmail(), "USER");

        // Création de la réponse
        AuthResponseDto response = new AuthResponseDto()
                .token(token)
                .role(RoleDto.USER)
                .user(userPublicDtoMapper.toPublicDto(savedUser));

        return ResponseEntity.ok(response);
    }



    @Override
    public ResponseEntity<AuthResponseDto> authLoginPost(@RequestBody AuthLoginPostRequestDto authLoginPostRequestDto) {
        String email = authLoginPostRequestDto.getEmail();

        //  Vérifier si l'utilisateur est temporairement bloqué
        if (bruteForceProtectionService.isBlocked(email)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body(null);
        }

        //  Vérification des identifiants
        User user = userPort.findUser(email, authLoginPostRequestDto.getMotDePasse());

        if (user != null) {
            //  Succès → Réinitialiser les tentatives
            bruteForceProtectionService.resetAttempts(email);

            String role = user.getRole();
            String token = jwtService.generateToken(user.getEmail(), role);

            UserPublicDto userPublicDto = new UserPublicDto()
                    .id(user.getId())
                    .email(user.getEmail())
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .role(role);

            return ResponseEntity.ok(new AuthResponseDto()
                    .token(token)
                    .role(RoleDto.valueOf(role))
                    .user(userPublicDto));
        }

        //  Échec → Incrémenter tentative
        bruteForceProtectionService.recordFailedAttempt(email);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }



}