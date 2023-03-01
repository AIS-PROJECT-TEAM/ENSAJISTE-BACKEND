package com.enjajiste.platform.utils;


import com.enjajiste.platform.models.User;
import com.enjajiste.platform.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticatedUser {

    public User getUser(UserService userService){
        User user = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth);
        String email = (String) auth.getPrincipal();
        System.out.println(auth.getPrincipal());
        user = userService.findByEmail(email);
        return user;
    }
}
