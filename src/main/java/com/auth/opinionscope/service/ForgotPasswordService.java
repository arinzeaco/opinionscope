package com.auth.opinionscope.service;

import com.auth.opinionscope.dataModel.ChangePasswordModel;
import com.auth.opinionscope.dataModel.ForgotPasswordModel;
import com.auth.opinionscope.model.User;
import com.auth.opinionscope.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class ForgotPasswordService {

    @Autowired
    private UserRepository usersRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<User> forgotPassword(ForgotPasswordModel forgotPasswordModel) {
        Optional<User> user = usersRepository.findByEmail(forgotPasswordModel.getEmail());
        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.get().setPassword(forgotPasswordModel.getPassword());

        usersRepository.save(user.get());
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
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
