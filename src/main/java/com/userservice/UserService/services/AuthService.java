package com.userservice.UserService.services;

import com.userservice.UserService.dtos.UserDto;
import com.userservice.UserService.models.SessionStatus;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<UserDto> login(String email, String password);
    UserDto signup(String email, String password);
    ResponseEntity<Void> logout(String token, Long userId);
    SessionStatus validate(Long userId, String token);
}
