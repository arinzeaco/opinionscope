package com.auth.opinionscope.service;

import com.auth.opinionscope.config.AuthenticationRequest;
import com.auth.opinionscope.config.AuthenticationResponse;
import com.auth.opinionscope.config.JwtService;
import com.auth.opinionscope.model.User;
import com.auth.opinionscope.model.token.Token;
import com.auth.opinionscope.model.token.TokenType;
//import com.auth.opinionscope.model.token.VerificationToken;
import com.auth.opinionscope.repository.TokenRepository;
import com.auth.opinionscope.repository.UserRepository;
//import com.auth.opinionscope.repository.VerificationTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/*
@Slf4j, is a Lombok-provided annotation that will automatically generate an SLF4J
Logger static property in the class at compilation time.
* */

@Slf4j
@Service
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
    private EmailService emailService;

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


    public AuthenticationResponse createUser(User users) {

        var user = User.builder()
                .firstname(users.getFirstname())
                .lastname(users.getLastname())
                .mobile_number(users.getMobile_number())
                .email(users.getEmail())
                .password(passwordEncoder.encode("12345678"))
//                .password(passwordEncoder.encode(users.getPassword()))
                .role(users.getRole())
                .email_verified(users.getEmail_verified())
                .mobile_number_verified(users.getMobile_number_verified())
                .build();

        var savedUser = usersRepository.save(user);
        var jwtToken = jwtService.generateJwtToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(savedUser, jwtToken,TokenType.BEARER);
        saveUserToken(savedUser, generateToken(),TokenType.CONFIRMATION);
        log.info("jwtToken");
        log.info(jwtToken);
//        saveUserEmailToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = usersRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateJwtToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken, TokenType.CONFIRMATION);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
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


}
