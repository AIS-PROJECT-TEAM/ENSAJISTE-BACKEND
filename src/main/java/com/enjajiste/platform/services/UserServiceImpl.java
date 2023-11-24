package com.enjajiste.platform.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.enjajiste.platform.models.Role;
import com.enjajiste.platform.models.User;
import com.enjajiste.platform.repositories.RoleRepository;
import com.enjajiste.platform.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		if (user == null) {
			log.error("User not found");
			throw new UsernameNotFoundException("User not found in the database");
		} else {
			log.info("User found in the database: {} ", user.getNom());
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		});
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}

	@Override
	public User saveUser(User user) {
		log.info("Saving new user {} to the database", user.getNom());
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public Role saveRole(Role role) {
		log.info("Saving a new role {} to the database", role.getName());
		return roleRepository.save(role);
	}

	@Override
	public void addRoleToUser(String cne, String roleName) {
		User user = userRepository.findByCne(cne);
		Role role = roleRepository.findByName(roleName);
		log.info("adding a role {} for a user {}", roleName, user.getNom());
		user.getRoles().add(role);
	}

	@Override
	public User getUser(String cne) {
		log.info("get a user by cne");
		return userRepository.findByCne(cne);
	}

	@Override
	public List<User> getUsers() {
		log.info("Get all users");
		return userRepository.findAll();
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

}
