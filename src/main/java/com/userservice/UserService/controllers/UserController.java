package com.userservice.UserService.controllers;

import com.userservice.UserService.dtos.SetUserRolesRequestDto;
import com.userservice.UserService.dtos.UserDto;
import com.userservice.UserService.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    UserController(UserService userService)
    {
        this.userService=userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserDetails(@PathVariable("id") Long userId) {
        UserDto userDto = this.userService.getUserDetails(userId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/{id}/roles")
    public ResponseEntity<UserDto> setUserRole(@PathVariable("id")Long userId, @RequestBody SetUserRolesRequestDto requestBody) {
        UserDto userDto = this.userService.setUserRoles(userId, requestBody.getRoleIds());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
