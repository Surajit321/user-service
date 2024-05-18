package com.userservice.UserService.security;

import com.userservice.UserService.models.User;
import com.userservice.UserService.repositiories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        if(optionalUser.isEmpty())
        {
            throw new UsernameNotFoundException("User with the above user name doesn't exist!");
        }
        User user = optionalUser.get();
        return new CustomUserDetails(user);
    }
}
