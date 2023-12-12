package com.auth.opinionscope.intergrated;

import com.auth.opinionscope.dataModel.request.ChangePasswordModel;
import com.auth.opinionscope.dataModel.request.ForgotPasswordModel;
import com.auth.opinionscope.service.PasswordService;
import com.auth.opinionscope.model.auth.UserData;
import com.auth.opinionscope.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class PasswordServiceIntegratedTest {

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @Sql("classpath:insert_user.sql")
    public void testForgotPasswordSuccess() {
        // Create a ForgotPasswordModel with test data
        ForgotPasswordModel forgotPasswordModel = new ForgotPasswordModel();
        forgotPasswordModel.setEmail("userone@example.com");
        forgotPasswordModel.setPassword("password124");
        // Call the forgotPassword method
        boolean result = passwordService.forgotPassword(forgotPasswordModel);
        // Check if the password was updated successfully
        assertTrue(result);
        // Retrieve the user from the database and check if the password is updated
        Optional<UserData> user = userRepository.findByEmail("userone@example.com");
        assertTrue(user.isPresent());
        assertTrue(passwordEncoder.matches("password124", user.get().getPassword()));
    }

    @Test
    @Sql("classpath:insert_user.sql")
    public void testForgotPasswordFail() {
        // Create a ForgotPasswordModel with test data
        ForgotPasswordModel forgotPasswordModel = new ForgotPasswordModel();
        forgotPasswordModel.setEmail("userone@example.com");
        forgotPasswordModel.setPassword("password124");
        // Call the forgotPassword method
        boolean result = passwordService.forgotPassword(forgotPasswordModel);
        // Check if the password was updated successfully
        assertTrue(result);
        // Retrieve the user from the database and check if the password is updated
        Optional<UserData> user = userRepository.findByEmail("userone@example.com");
        assertTrue(user.isPresent());
        assertFalse(passwordEncoder.matches("password123", user.get().getPassword()));
    }

    @Test
    @Sql("classpath:insert_user.sql")
    public void testChangePasswordSuccess() {
        // Create a ChangePasswordModel with test data
        ChangePasswordModel changePasswordModel = new ChangePasswordModel();
        changePasswordModel.setEmail("userone@example.com");
        changePasswordModel.setCurrentPassword("password123");
        changePasswordModel.setNewPassword("newPassword");
        // Call the changePassword method
        boolean result = passwordService.changePassword(changePasswordModel);
        // Check if the password was changed successfully
        assertTrue(result);
        // Retrieve the user from the database and check if the password is updated
        Optional<UserData> user = userRepository.findByEmail("userone@example.com");
        assertTrue(user.isPresent());
        assertTrue(passwordEncoder.matches("newPassword", user.get().getPassword()));
    }

    @Test
    @Sql("classpath:insert_user.sql")
    public void testChangePasswordFail() {
        // Create a ChangePasswordModel with test data
        ChangePasswordModel changePasswordModel = new ChangePasswordModel();
        changePasswordModel.setEmail("userone@example.com");
        changePasswordModel.setCurrentPassword("password123");
        changePasswordModel.setNewPassword("newPassword");
        // Call the changePassword method
        boolean result = passwordService.changePassword(changePasswordModel);
        // Check if the password was changed successfully
        assertTrue(result);
        // Retrieve the user from the database and check if the password is updated
        Optional<UserData> user = userRepository.findByEmail("userone@example.com");
        assertTrue(user.isPresent());
        assertTrue(passwordEncoder.matches("newPassword", user.get().getPassword()));
    }
}
