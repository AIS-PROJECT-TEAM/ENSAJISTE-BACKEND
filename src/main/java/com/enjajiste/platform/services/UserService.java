package com.enjajiste.platform.services;

import com.enjajiste.platform.models.Role;
import com.enjajiste.platform.models.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String cne, String roleName);
    User getUser(String cne);
    List<User> getUsers();
    User findByEmail(String email);
}
