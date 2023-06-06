package com.auth.opinionscope.service;

import com.auth.opinionscope.config.AuthenticationRequest;
import com.auth.opinionscope.config.JwtService;
import com.auth.opinionscope.model.User;
import com.auth.opinionscope.model.token.Token;
import com.auth.opinionscope.model.token.TokenType;
//import com.auth.opinionscope.model.token.VerificationToken;
import com.auth.opinionscope.repository.TokenRepository;
import com.auth.opinionscope.repository.UserRepository;
//import com.auth.opinionscope.repository.VerificationTokenRepository;
import com.auth.opinionscope.rest.JwtWithResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Random;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository usersRepository;

//    @Autowired
//    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private EmailVerificationService emailVerificationService;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    public boolean checkIfUserAlreadyExist(User users) {

        if (usersRepository.existsByEmail(users.getEmail())) {
            return true;
        }
        return false;
    }

    public JwtWithResponse createUser(User users) {
        log.info("users.getPassword()");

        var user = User.builder()
                .firstname(users.getFirstname())
                .lastname(users.getLastname())
                .mobile_number(users.getMobile_number())
                .password(passwordEncoder.encode(users.getPassword()))
                .email(users.getEmail())
                .role(users.getRole())
                .email_verified(users.getEmail_verified())
                .mobile_number_verified(users.getMobile_number_verified())
                .build();

        var savedUser = usersRepository.save(user);
        var jwtToken = jwtService.generateJwtToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(savedUser, jwtToken, TokenType.BEARER);
        log.info("jwtToken");
        log.info(jwtToken);
        emailVerificationService.createVerification(users.getEmail());

        JwtWithResponse response = new JwtWithResponse();
        response.setStatusCode("200");
        response.setStatusMsg("User successfully registered");
        response.setData(users);
        response.setAccess_token(jwtToken);
        response.setRefresh_token(refreshToken);

        return response;
    }

    public JwtWithResponse authenticate(AuthenticationRequest request) {
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getEmail(),
//                        request.getPassword()
//                )
//        );
//
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var user = usersRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateJwtToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);

        JwtWithResponse response = new JwtWithResponse();
        response.setStatusCode("200");
        response.setStatusMsg("User succesfully registered");
        response.setData(user);
        response.setAccess_token(jwtToken);
        response.setRefresh_token(refreshToken);
        return response;
    }

    public String generateToken() {
        Random random = new Random();
        int min = 100000; // Minimum 6-digit number
        int max = 999999; // Maximum 6-digit number
        int randomNumber = random.nextInt(max - min + 1) + min;
        return String.valueOf(randomNumber);
    }

    private void saveUserToken(User user, String tokenVal, TokenType confirmation) {
        var token = Token.builder()
                .user(user)
                .token(tokenVal)
                .tokenType(confirmation)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

//    private void saveUserEmailToken(User user) {
//        var myToken = VerificationToken.builder()
//                .user(user)
//                .confirmationToken(generateToken())
//                .expiryDate(new Date(System.currentTimeMillis() + 3600000))
//                .build();
//        verificationTokenRepository.save(myToken);
//    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getUserId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public List<User> getUser() {

        List<User> savedUser = usersRepository.findAll();
        return savedUser;
    }

    public boolean verifyEmail(String email) {
        Optional<User> user = usersRepository.findByEmail(email);
        if (user.isEmpty()) {
            return false;
        }
        user.get().setEmail_verified(true);

        User savedUser = usersRepository.save(user.get());
        return savedUser.getUserId() != null; // Return true if save operation was successful
    }
}
