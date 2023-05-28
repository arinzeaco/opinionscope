package com.auth.opinionscope.service;

import com.auth.opinionscope.model.User;
//import com.auth.opinionscope.model.token.VerificationToken;
//import com.auth.opinionscope.repository.VerificationTokenRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

//    @Autowired
//    private VerificationTokenRepository verificationTokenRepository;


    private static final int TOKEN_EXPIRATION_HOURS = 24;


    public String generateToken() {
        Random random = new Random();
        int min = 100000; // Minimum 6-digit number
        int max = 999999; // Maximum 6-digit number
        int randomNumber = random.nextInt(max - min + 1) + min;
        return String.valueOf(randomNumber);
    }
//    public VerificationToken getVerificationToken(String VerificationToken) {
//        return verificationTokenRepository.findByToken(VerificationToken);
//    }


//    public void createVerificationToken(User user) {
//        VerificationToken myToken = new VerificationToken();
//        myToken.setUser(user);
//        myToken.setToken(generateToken());
//
//        verificationTokenRepository.save(myToken);
//    }
//    public void createToken(User user) {
//        String token = generateToken();
//        LocalDateTime expirationDate = LocalDateTime.now().plusHours(TOKEN_EXPIRATION_HOURS);
//        EmailVerificationToken emailVerificationTokens=new EmailVerificationToken();
//        emailVerificationTokens.setConfirmationToken(token);
//        emailVerificationTokens.setUser(user);
//        emailVerificationTokens.setCreatedAt(expirationDate);
//
//        confirmationTokenRepository.save(emailVerificationTokens);
//        // Send the token to the user's email address
//    }

//    public boolean validateToken(String token) {
//        Optional<VerificationToken> tokenOptional = verificationTokenRepository.findByToken(token);
//        if (tokenOptional.isPresent()) {
//            VerificationToken verificationToken = tokenOptional.get();
//            if (!verificationToken.isVerified() && verificationToken.getExpirationDate().isAfter(LocalDateTime.now())) {
//                verificationToken.setVerified(true);
//                tokenRepository.save(verificationToken);
//                // Perform any additional actions upon successful token validation
//                return true;
//            }
//        }
//        return false;
//    }


    public void sendMail(@Valid User user) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");

//        mailMessage.setText("Hello, "+ user.getFirstname()+"\n\n"
//                + "Welcome to opinionscope, this is you email token. \n"
//                + "Best regards,\n");
        mailMessage.setText("To confirm your account, please click here : "
                +"http://localhost:8085/confirm-account?token="+"confirmationToken.getConfirmationToken()");
        javaMailSender.send(mailMessage);

    }
}
