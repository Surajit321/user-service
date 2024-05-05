package com.userservice.UserService.controllers;

import com.userservice.UserService.services.RoleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private RoleService roleService;

    RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public String createRole() {
        return this.roleService.createRole();
    }
}
