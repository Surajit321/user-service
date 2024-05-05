package com.userservice.UserService.controllers;

import com.userservice.UserService.services.UserService;
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
    public String getUserDetails(@PathVariable("id") Long userId) {
       return this.userService.getUserDetails(userId);
    }

    @PostMapping("/{id}/roles")
    public String setUserRole(@PathVariable("id") Long userId) {
        return this.userService.setUserRoles(userId);
    }
}
