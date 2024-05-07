package com.userservice.UserService.controllers;

import com.userservice.UserService.dtos.CreateRoleRequestDto;
import com.userservice.UserService.models.Role;
import com.userservice.UserService.services.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<Role> createRole(@RequestBody CreateRoleRequestDto requestBody) {
        Role role = this.roleService.createRole(requestBody.getName());
        return new ResponseEntity<>(role, HttpStatus.OK);
    }
}
