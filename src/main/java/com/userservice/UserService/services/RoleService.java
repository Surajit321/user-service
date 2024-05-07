package com.userservice.UserService.services;

import com.userservice.UserService.models.Role;
import com.userservice.UserService.repositiories.RoleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createRole(String name)
    {
       Role role = new Role();
       role.setRole(name);
       return roleRepository.save(role);
    }
}
