package com.userservice.UserService.services;

public interface AuthService {
    String login(String email, String password);
    String signup(String email, String password);
    String logout();
    String validate();
}
