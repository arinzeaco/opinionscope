package com.auth.opinionscope.service;

import com.auth.opinionscope.config.JwtService;
import com.auth.opinionscope.model.auth.UserData;
import com.auth.opinionscope.repository.TokenRepository;
import com.auth.opinionscope.repository.UserRepository;
import com.auth.opinionscope.rest.JwtWithResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import java.sql.Date;
import static com.auth.opinionscope.model.Role.USERS;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private EmailVerificationService emailVerificationService;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCheckIfUserAlreadyExist() {
        UserData existingUser = new UserData();
        existingUser.setEmail("existing@example.com");

        when(userRepository.existsByEmail(existingUser.getEmail())).thenReturn(true);

        assertTrue(userService.checkIfUserAlreadyExist(existingUser));

        verify(userRepository, times(1)).existsByEmail(existingUser.getEmail());
    }

    @Test
    public void createUser() {
        // Create a mock userData object
        UserData userData = new UserData();
        userData.setEmail("user@example.com");
        userData.setPassword("1234567890");
        userData.setMobile_number("1234567890");
        userData.setCountry("United States");
        userData.setFirstname("John");
        userData.setLastname("Doe");
        userData.setDateOfBirth(Date.valueOf("1991-02-02"));
        userData.setGoogleAuthId(null);
        userData.setGoogleAuthSecret(null);
        userData.setEmail_verified(false);
        userData.setMobile_number_verified(false);
        userData.setRole(USERS);
        when(passwordEncoder.encode(userData.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any())).thenReturn(userData);
        when(jwtService.generateJwtToken(any())).thenReturn("jwtToken");
        when(jwtService.generateRefreshToken(any())).thenReturn("refreshToken");

        JwtWithResponse response = userService.createUser(userData);

        assertNotNull(response);
        assertEquals("200", response.getStatusCode());
        assertEquals("User successfully registered", response.getStatusMsg());
        assertEquals(userData, response.getData());
        assertEquals("jwtToken", response.getAccess_token());
        assertEquals("refreshToken", response.getRefresh_token());

        verify(userRepository, times(1)).save(any());
        verify(jwtService, times(1)).generateJwtToken(any());
        verify(jwtService, times(1)).generateRefreshToken(any());
        verify(emailVerificationService, times(1)).createVerification(userData.getEmail());
        verify(tokenRepository, times(1)).save(any());
    }

//    @Test
//    public void createUser() {
//        // Create a mock userData object
//        UserData userData = new UserData();
//        userData.setEmail("user@example.com");
//        userData.setPassword("1234567890");
//        userData.setMobile_number("1234567890");
//        userData.setCountry("United States");
//        userData.setFirstname("John");
//        userData.setLastname("Doe");
//        userData.setDateOfBirth(Date.valueOf("1991-02-02"));
//        userData.setGoogleAuthId(null);
//        userData.setGoogleAuthSecret(null);
//        userData.setEmail_verified(false);
//        userData.setMobile_number_verified(false);
//        userData.setRole(USERS);
//        userService.createUser(userData);
//
//        JwtWithResponse response = userService.createUser(userData);
//
//        assertThat(response.getStatusCode()).isEqualTo("200");
//        assertThat(response.getStatusMsg()).isEqualTo("User successfully registered");
//
//        assertThat(response.getData().getUserId()).isNotNull();
//    }
    @Test
    void checkIfUserAlreadyExist() {
    }

}