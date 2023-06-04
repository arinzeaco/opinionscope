package com.auth.opinionscope.repository;

import com.auth.opinionscope.model.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {
    EmailVerification findByEmail(String email);
}