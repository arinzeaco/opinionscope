package com.auth.opinionscope.service;

import com.auth.opinionscope.dataModel.request.ChangePasswordModel;
import com.auth.opinionscope.dataModel.request.ForgotPasswordModel;
import com.auth.opinionscope.model.auth.UserData;
import com.auth.opinionscope.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
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
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;



    public boolean forgotPassword(ForgotPasswordModel forgotPasswordModel) {
        Optional<UserData> user = usersRepository.findByEmail(forgotPasswordModel.getEmail());
        if (user.isEmpty()) {
            return false;
        }
        user.get().setPassword(passwordEncoder.encode(forgotPasswordModel.getPassword()));

        UserData savedUserData = usersRepository.save(user.get());
//        return true;

        return savedUserData.getUserId() != null;
    }

    public boolean changePassword(ChangePasswordModel changePasswordModel) {
        Optional<UserData> user = usersRepository.findByEmail(changePasswordModel.getEmail());
        if (user.isEmpty()) {
            return false;
        }

        try {
            // Attempt authentication
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            changePasswordModel.getEmail(),
                            changePasswordModel.getCurrentPassword()
                    )
            );

            // Check if the user exists and the current password is correct
            if (passwordEncoder.matches(changePasswordModel.getCurrentPassword(), user.get().getPassword())) {
                // Update the user's password
                String encodedNewPassword = passwordEncoder.encode(changePasswordModel.getNewPassword());
                user.get().setPassword(encodedNewPassword);
                usersRepository.save(user.get());
                return true;
            }
        } catch (AuthenticationException e) {
            // Authentication failed
            return false;
        }

        return false;

    }
}
