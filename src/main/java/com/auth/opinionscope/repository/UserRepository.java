package com.auth.opinionscope.repository;

import com.auth.opinionscope.model.Questions;
import com.auth.opinionscope.model.auth.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserData, Integer> {
//    List<User> findAll();
    Optional<UserData> findByEmail(String Email);
    Optional<UserData> findByUserId(Long UserId);
    Boolean existsByEmail(String email);

}
