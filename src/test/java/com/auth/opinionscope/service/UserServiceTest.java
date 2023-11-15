package com.auth.opinionscope.service;

import com.auth.opinionscope.config.AuthenticationRequest;
import com.auth.opinionscope.config.JwtService;
import com.auth.opinionscope.model.auth.UserData;
import com.auth.opinionscope.repository.TokenRepository;
import com.auth.opinionscope.repository.UserRepository;
import com.auth.opinionscope.rest.JwtWithResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;

import static com.auth.opinionscope.model.Role.USERS;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    @Mock
    UserDetailsService userDetailsService;

    @Test
    void testCheckIfUserDoesNotExist() {
        UserData existingUser = new UserData();
        existingUser.setEmail("existing@example.com");

        when(userRepository.existsByEmail(existingUser.getEmail())).thenReturn(false);

        assertFalse(userService.checkIfUserAlreadyExist(existingUser));

        verify(userRepository, times(1)).existsByEmail(existingUser.getEmail());
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


    @Test
    public void testAuthenticateFail() {
        // Mock authentication request
        AuthenticationRequest mockRequest = new AuthenticationRequest();
        mockRequest.setEmail("test@example.com");
        mockRequest.setPassword("password");

        Authentication mockAuthentication = Mockito.mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(mockAuthentication);

        when(userService.authenticate(mockRequest)).thenReturn(null);
        JwtWithResponse response = userService.authenticate(mockRequest);

        assertEquals("400", response.getStatusCode());
        assertEquals("User not found", response.getStatusMsg());
    }

//    @Test
//    public void testSuccessfulLogin() {
//
//        // Mock authentication request
//        AuthenticationRequest mockRequest = new AuthenticationRequest();
//        mockRequest.setEmail("test@example.com");
//        mockRequest.setPassword("password");
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_USER")); // Add any roles or authorities you need
//        UserDetails mockUserDetails = new org.springframework.security.core.userdetails.User(
//                mockRequest.getEmail(),
//                "encodedPassword", // This should match your real password encoder
//                authorities
//        );
//
//        // Mock user details
//
//
//        // Mock authentication result
//        Authentication mockAuthentication = Mockito.mock(Authentication.class);
//        when(authenticationManager.authenticate(Mockito.any()))
//                .thenReturn(mockAuthentication);
//        SecurityContextHolder.getContext().setAuthentication(mockAuthentication);
//
//
//        // Mock revokeAllUserTokens method (if it's in the same class or accessible)
//        JwtWithResponse mockUserData = new JwtWithResponse();
//        mockUserData.setStatusCode("200");
//        mockUserData.setStatusMsg("User successfully LoggedIn");
//        userDetailsService.loadUserByUsername(mockRequest.getEmail());
//
//        assertEquals(true, mockAuthentication.isAuthenticated());
//
//        // Call the authenticate method
//    }

    @Test
    void checkIfUserAlreadyExist() {
    }

}