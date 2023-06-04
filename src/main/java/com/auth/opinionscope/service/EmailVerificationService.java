package com.auth.opinionscope.service;

import com.auth.opinionscope.model.EmailVerification;
import com.auth.opinionscope.model.User;
import com.auth.opinionscope.repository.EmailVerificationRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class EmailVerificationService {
    @Autowired
    private JavaMailSender javaMailSender;
    private final EmailVerificationRepository emailVerificationRepository;

    @Autowired
    public EmailVerificationService(EmailVerificationRepository emailVerificationRepository) {
        this.emailVerificationRepository = emailVerificationRepository;
    }

    public void createVerification(String email, String otp) {
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setEmail(email);
        emailVerification.setOtp(otp);
        emailVerification.setExpirationTime(LocalDateTime.now().plusMinutes(10));
        log.info("PPPPPPPP The pass"+ otp);

        emailVerificationRepository.save(emailVerification);

//        sendMail(email,otp);
        // Send the email with the OTP to the user's email address
    }

    public boolean validateOTP(String email, String otp) {
        EmailVerification emailVerification = emailVerificationRepository.findByEmail(email);
        if (emailVerification != null && emailVerification.getOtp().equals(otp)) {
            LocalDateTime currentTime = LocalDateTime.now();
            if (currentTime.isBefore(emailVerification.getExpirationTime())) {
                // OTP is valid and not expired
                // Perform any additional actions needed for successful validation
                log.info("PPPPPPPP The pass");
                return true;
            }
        }
        return false;
    }

    public void sendMail(String email, String otp) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Complete Registration!");

//        mailMessage.setText("Hello, "+ user.getFirstname()+"\n\n"
//                + "Welcome to opinionscope, this is you email token. \n"
//                + "Best regards,\n");
        mailMessage.setText("To confirm your account, please click here : "
                +"http://localhost:8085/confirm-account?token="+otp);
        javaMailSender.send(mailMessage);

    }
}