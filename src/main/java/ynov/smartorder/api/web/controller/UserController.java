package ynov.smartorder.api.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import smartorder.alison_api.web.dtos.AuthLoginPost200ResponseDto;
import smartorder.alison_api.web.dtos.AuthLoginPostRequestDto;
import smartorder.alison_api.web.dtos.UserDto;
import smartorder.alison_api.web.apis.UsersApi;
@RequiredArgsConstructor
@RestController
public class UserController implements UsersApi {

    @Override
    public ResponseEntity<AuthLoginPost200ResponseDto> authLoginPost(AuthLoginPostRequestDto authLoginPostRequestDto) {
        return null;
    }

    @Override
    public ResponseEntity<Void> authRegisterPost(UserDto userDto) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteUser(String email) {
        return null;
    }
}
