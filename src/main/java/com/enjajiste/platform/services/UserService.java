package com.enjajiste.platform.services;

import java.util.List;

import com.enjajiste.platform.models.Role;
import com.enjajiste.platform.models.User;

public interface UserService {

	User saveUser(User user);

	Role saveRole(Role role);

	void addRoleToUser(String cne, String roleName);

	User getUser(String cne);

	List<User> getUsers();

	User findByEmail(String email);
}
