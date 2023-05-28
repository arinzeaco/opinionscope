//package com.auth.opinionscope.repository;
//
//import com.auth.opinionscope.model.User;
//import com.auth.opinionscope.model.token.VerificationToken;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.Optional;
//
//public interface VerificationTokenRepository
//  extends JpaRepository<VerificationToken, Integer> {
//    Optional<VerificationToken> findByToken(String Email);
//    //
////    VerificationToken findByUser(User user);
//}