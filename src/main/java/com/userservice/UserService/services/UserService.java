package com.userservice.UserService.services;

import org.springframework.stereotype.Service;

@Service
public class UserService {


    public String getUserDetails(Long userId)
    {
        return "from userdetails";
    }

    public String setUserRoles(Long id)
    {
        return "from setuserroles";
    }
}
