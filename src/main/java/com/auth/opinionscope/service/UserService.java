package com.auth.opinionscope.service;

import com.auth.opinionscope.config.AuthenticationRequest;
import com.auth.opinionscope.config.JwtService;
import com.auth.opinionscope.model.auth.UserData;
import com.auth.opinionscope.model.auth.UsersDetails;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDetailsService userDetailsService;

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


    public boolean checkIfUserAlreadyExist(UserData users) {

        if (usersRepository.existsByEmail(users.getEmail())) {
            return true;
        }
        return false;
    }

    public JwtWithResponse createUser(UserData users) {
        log.info("users.getPassword()");
        UsersDetails usersDetailsData = new UsersDetails(); // Initialize the usersDetails field with default values
        usersDetailsData.setProfileImageUrl("");
        var user = UserData.builder()
                .firstname(users.getFirstname())
                .lastname(users.getLastname())
                .mobile_number(users.getMobile_number())
                .password(passwordEncoder.encode(users.getPassword()))
                .email(users.getEmail())
                .role(users.getRole())
                .email_verified(users.getEmail_verified())
                .mobile_number_verified(users.getMobile_number_verified())
//                .usersDetails(usersDetailsData)
                .build();

        var savedUser = usersRepository.save(user);
        var jwtToken = jwtService.generateJwtToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(savedUser, jwtToken, TokenType.BEARER);
        log.info("jwtToken");
//        log.info(savedUser.getUserId().toString());
        users.setUserId(user.getUserId());
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
        Optional<UserData>  userDetails= usersRepository.findByEmail(request.getEmail());
//        if (!userDetails.isPresent()) {
//            JwtWithResponse response = new JwtWithResponse();
//            response.setStatusCode("400");
//            response.setStatusMsg("User not found");
//            return response;
//
////            throw new UserNotFoundException("User not found");
//        }
        if (userDetails.isEmpty() || !passwordEncoder.matches(request.getPassword(), userDetails.get().getPassword())) {
            JwtWithResponse response = new JwtWithResponse();
            response.setStatusCode("400");
            response.setStatusMsg("User not found");
            return response;

//            throw new UserNotFoundException("User not found");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
         Optional<UserData>  user= usersRepository.findByEmail(request.getEmail());
        var jwtToken = jwtService.generateJwtToken(user.get());
        var refreshToken = jwtService.generateRefreshToken(user.get());
        revokeAllUserTokens(user.get());

        JwtWithResponse response = new JwtWithResponse();
        response.setStatusCode("200");
        response.setStatusMsg("User successfully LoggedIn");
        response.setData(user);
        response.setAccess_token(jwtToken);
        response.setRefresh_token(refreshToken);

        return response;

    }

//    public Optional<UserData> findByUserId(long userId) {
//        Optional<UserData>  user = usersRepository.findByUserId(userId);
//        if(!user.isPresent()){
//           return user;
//        }
//        return user;
////        Response response = new Response();
////        response.setStatusCode("200");
////        response.setStatusMsg("User successfully registered");
////        response.setData(user.get());
////        return ResponseEntity.ok(response);
//    }

    private void saveUserToken(UserData UserData, String tokenVal, TokenType confirmation) {
        var token = Token.builder()
                .UserData(UserData)
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

    public void revokeAllUserTokens(UserData UserData) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(UserData.getUserId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public List<UserData> getUser() {

        List<UserData> savedUserData = usersRepository.findAll();
        return savedUserData;
    }

    public boolean verifyEmail(String email) {
        Optional<UserData> user = usersRepository.findByEmail(email);
        if (user.isEmpty()) {
            return false;
        }
        user.get().setEmail_verified(true);

        UserData savedUserData = usersRepository.save(user.get());
        return savedUserData.getUserId() != null; // Return true if save operation was successful
    }

    public UserData updateUser(UserData UserData) {
        return usersRepository.save(UserData);
    }
}
