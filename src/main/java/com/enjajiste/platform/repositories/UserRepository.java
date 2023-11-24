package com.enjajiste.platform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enjajiste.platform.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findById(int id);

	User findByCne(String cne);

	User findByEmail(String email);
}
