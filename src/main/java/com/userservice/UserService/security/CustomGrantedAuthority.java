package com.userservice.UserService.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.userservice.UserService.models.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@JsonDeserialize(as= CustomGrantedAuthority.class)
@NoArgsConstructor
@Getter
@Setter
public class CustomGrantedAuthority implements GrantedAuthority {
    private Role role;

    CustomGrantedAuthority(Role role)
    {
        this.role = role;
    }
    @Override
    @JsonIgnore
    public String getAuthority() {
        return role.getRole();
    }
}
