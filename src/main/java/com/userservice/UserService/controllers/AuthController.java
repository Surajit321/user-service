package com.userservice.UserService.controllers;

import com.userservice.UserService.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
    public String login(String email, String password) {
        return this.authService.login(email, password);
    }

    @PostMapping("/signup")
    public String signup(String email, String password) {
        return this.authService.signup(email, password);
    }

    @PostMapping("/logout")
    public String logout() {
        return this.authService.logout();
    }

    @PostMapping("/validate")
    public String validateToken() {
        return this.authService.validate();
    }
}
