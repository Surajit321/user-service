package com.userservice.UserService.controllers;

import com.userservice.UserService.dtos.*;
import com.userservice.UserService.models.SessionStatus;
import com.userservice.UserService.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto requestBody) {
        return this.authService.login(requestBody.getEmail(), requestBody.getPassword());
    }

    @PostMapping("/signup")
    public UserDto signup(@RequestBody SignUpRequestDto requestBody) {
        return this.authService.signup(requestBody.getEmail(), requestBody.getPassword());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto requestBody) {
        return this.authService.logout(requestBody.getToken(), requestBody.getUserId());
    }

    @PostMapping("/validate")
    public SessionStatus validateToken(@RequestBody ValidateTokenDto requestBody) {
        return this.authService.validate(requestBody.getUserId(), requestBody.getToken());
    }
}
