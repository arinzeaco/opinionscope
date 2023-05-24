package com.auth.opinionscope.service;

import com.auth.opinionscope.config.AuthenticationRequest;
import com.auth.opinionscope.config.AuthenticationResponse;
import com.auth.opinionscope.config.JwtService;
import com.auth.opinionscope.model.User;
import com.auth.opinionscope.model.token.Token;
import com.auth.opinionscope.model.token.TokenType;
import com.auth.opinionscope.repository.TokenRepository;
import com.auth.opinionscope.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/*
@Slf4j, is a Lombok-provided annotation that will automatically generate an SLF4J
Logger static property in the class at compilation time.
* */

@Service
public class UserService {

    @Autowired
    private UserRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private  AuthenticationManager authenticationManager;



    public AuthenticationResponse createUser(User users){
        var user = User.builder()
                .firstname(users.getFirstname())
                .lastname(users.getLastname())
                .mobile_number(users.getMobile_number())
                .email(users.getEmail())
                .password(passwordEncoder.encode(users.getPassword()))
                .role(users.getRole())
                .email_verified(users.getEmail_verified())
                .phonenumber_verified(users.getPhonenumber_verified())
                .build();
        var savedUser = usersRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
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
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }
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

    public List<User> getUser(){

        List<User> savedUser= usersRepository.findAll();
        return  savedUser;
    }


}
