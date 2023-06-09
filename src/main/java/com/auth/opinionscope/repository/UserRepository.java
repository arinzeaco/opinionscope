package com.auth.opinionscope.repository;

import com.auth.opinionscope.model.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
//    List<User> findAll();
    Optional<User> findByEmail(String Email);
    Optional<User> findByUserId(Long UserId);
    Boolean existsByEmail(String email);

}
