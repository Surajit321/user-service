package com.userservice.UserService.services;

import com.userservice.UserService.dtos.UserDto;
import com.userservice.UserService.models.Role;
import com.userservice.UserService.models.User;
import com.userservice.UserService.repositiories.RoleRepository;
import com.userservice.UserService.repositiories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository)
    {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    public UserDto getUserDetails(Long userId)
    {
         Optional<User> user = this.userRepository.findById(userId);

         if(user.isEmpty())
         {
             return null;
         }

        return UserDto.from(user.get());
    }

    public UserDto setUserRoles(Long id, List<Long> roleIds)
    {
        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isEmpty())
        {
            return null;
        }

        User user = optionalUser.get();

        List<Role> roles = roleRepository.findAllByIdIn(roleIds);
        user.setRoles(Set.copyOf(roles));
        User savedUser = userRepository.save(user);

        return UserDto.from(savedUser);
    }
}
