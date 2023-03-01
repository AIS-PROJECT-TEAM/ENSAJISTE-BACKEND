package com.enjajiste.platform.repositories;

import com.enjajiste.platform.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    User findById(int id);
    User findByCne(String cne);
    User findByEmail(String email);
}
