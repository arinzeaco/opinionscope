package com.auth.opinionscope.repository;

import com.auth.opinionscope.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
//    List<User> findAll();
    Optional<User> findByEmail(String Email);
    Boolean existsByEmail(String email);


}
