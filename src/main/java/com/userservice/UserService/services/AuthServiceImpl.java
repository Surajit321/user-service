package com.userservice.UserService.services;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{
    @Override
    public String login(String email, String password) {
        return "Hi, from login";
    }

    @Override
    public String signup(String email, String password) {
        return "Hi, from signup";
    }

    @Override
    public String logout() {
        return "Hi, from logout";
    }

    @Override
    public String validate() {
        return "Hi, from validate";
    }
}
