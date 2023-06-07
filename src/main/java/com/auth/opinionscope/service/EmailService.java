//package com.auth.opinionscope.service;
//
//import com.auth.opinionscope.model.auth.User;
////import com.auth.opinionscope.model.token.VerificationToken;
////import com.auth.opinionscope.repository.VerificationTokenRepository;
//import jakarta.validation.Valid;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//import java.util.Random;
//
//@Service
//@AllArgsConstructor
//public class EmailService {
//    @Autowired
//    private JavaMailSender javaMailSender;
//
////    @Autowired
////    private VerificationTokenRepository verificationTokenRepository;
//
//
//    private static final int TOKEN_EXPIRATION_HOURS = 24;
//
//
//
//
//
////    public boolean validateToken(String token) {
////        Optional<VerificationToken> tokenOptional = verificationTokenRepository.findByToken(token);
////        if (tokenOptional.isPresent()) {
////            VerificationToken verificationToken = tokenOptional.get();
////            if (!verificationToken.isVerified() && verificationToken.getExpirationDate().isAfter(LocalDateTime.now())) {
////                verificationToken.setVerified(true);
////                tokenRepository.save(verificationToken);
////                // Perform any additional actions upon successful token validation
////                return true;
////            }
////        }
////        return false;
////    }
//
//
//
//}
