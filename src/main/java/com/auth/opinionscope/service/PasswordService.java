package com.auth.opinionscope.service;

import com.auth.opinionscope.dataModel.request.ChangePasswordModel;
import com.auth.opinionscope.dataModel.request.ForgotPasswordModel;
import com.auth.opinionscope.model.auth.User;
import com.auth.opinionscope.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
@Slf4j, is a Lombok-provided annotation that will automatically generate an SLF4J
Logger static property in the class at compilation time.
* */

@Slf4j
@Service
public class PasswordService {

    @Autowired
    private UserRepository usersRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;



    public boolean forgotPassword(ForgotPasswordModel forgotPasswordModel) {
        Optional<User> user = usersRepository.findByEmail(forgotPasswordModel.getEmail());
        if (user.isEmpty()) {
            return false;
        }
        user.get().setPassword(passwordEncoder.encode(forgotPasswordModel.getPassword()));

        User savedUser = usersRepository.save(user.get());
        return savedUser.getUserId() != null;
    }

    public boolean changePassword(ChangePasswordModel changePasswordModel) {
        var user = usersRepository.findByEmail(changePasswordModel.getEmail())
                .orElseThrow();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        changePasswordModel.getEmail(),
                        changePasswordModel.getCurrentPassword()
                )
        );
        // Check if the user exists and the current password is correct
        if (user != null &&
                passwordEncoder.matches(changePasswordModel.getCurrentPassword(), user.getPassword())) {
            // Update the user's password
            String encodedNewPassword = passwordEncoder.encode(changePasswordModel.getNewPassword());
            user.setPassword(encodedNewPassword);
            usersRepository.save(user);
            return true;
        }

        return false;
    }
}
