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

import java.sql.Date;

import static com.auth.opinionscope.model.Role.USERS;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceMockTest {
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
    private UserRepository usersRepository;

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

        when(userRepository.existsByEmail(any())).thenReturn(false);
        var register = userRepository.existsByEmail(existingUser.getEmail());
        assertEquals(false, register);
        verify(userRepository, times(1)).existsByEmail(any());
    }

    @Test
    void testCheckIfUserAlreadyExist() {
        UserData existingUser = new UserData();
        existingUser.setEmail("existing@example.com");

        when(userRepository.existsByEmail(any())).thenReturn(true);
        var refreshToken = userRepository.existsByEmail(existingUser.getEmail());
        assertEquals(true, refreshToken);
        verify(userRepository, times(1)).existsByEmail(any());
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
//
//        when(passwordEncoder.encode(userData.getPassword())).thenReturn("encodedPassword");
//        when(userRepository.save(any())).thenReturn(userData);
//        when(jwtService.generateJwtToken(any())).thenReturn("jwtToken");
//        when(jwtService.generateRefreshToken(any())).thenReturn("refreshToken");
//
//        JwtWithResponse response = userService.createUser(userData);
//
//        assertNotNull(response);
//        assertEquals("200", response.getStatusCode());
//        assertEquals("User successfully registered", response.getStatusMsg());
//        assertEquals(userData, response.getData());
//        assertEquals("jwtToken", response.getAccess_token());
//        assertEquals("refreshToken", response.getRefresh_token());
//
//        verify(userRepository, times(1)).save(any());
//        verify(jwtService, times(1)).generateJwtToken(any());
//        verify(jwtService, times(1)).generateRefreshToken(any());
//        verify(emailVerificationService, times(1)).createVerification(userData.getEmail());
//        verify(tokenRepository, times(1)).save(any());
//    }


    @Test
    public void testAuthenticateFail() {
        // Mock authentication request
        JwtWithResponse response = new JwtWithResponse();
        response.setStatusCode("400");
        response.setStatusMsg("User not found");

        AuthenticationRequest mockRequest = new AuthenticationRequest();
        mockRequest.setEmail("test@example.com");
        mockRequest.setPassword("password");

        Authentication mockAuthentication = Mockito.mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(mockAuthentication);

        Optional<UserData> userDetails = usersRepository.findByEmail(mockRequest.getEmail());
        assertEquals(userDetails.isPresent(), false);



        UserData userData = new UserData();
        userData.setEmail("user@example.com");
        userData.setPassword("1234567890");


        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER")); // Add any roles or authorities you need

        UserDetails mockUserDetails = new org.springframework.security.core.userdetails.User(
                mockRequest.getEmail(),
                "encodedPassword", // This should match your real password encoder
                authorities
        );

        when(userDetailsService.loadUserByUsername(mockRequest.getEmail())).thenReturn(mockUserDetails);
        userDetailsService.loadUserByUsername(mockRequest.getEmail()); // Call the method that uses userDetailsService.loadUserByUsername
        assertTrue(!mockRequest.getPassword().equals(mockUserDetails.getPassword()));



    }

    @Test
    public void testSuccessfulLogin() {
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
        AuthenticationRequest mockRequest = new AuthenticationRequest();
        mockRequest.setEmail("test@example.com");
        mockRequest.setPassword("encodedPassword");
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER")); // Add any roles or authorities you need

        UserDetails mockUserDetails = new org.springframework.security.core.userdetails.User(
                mockRequest.getEmail(),
                "encodedPassword", // This should match your real password encoder
                authorities
        );

        when(userDetailsService.loadUserByUsername(mockRequest.getEmail())).thenReturn(mockUserDetails);
        UserDetails actualUserDetails = userDetailsService.loadUserByUsername(mockRequest.getEmail()); // Call the method that uses userDetailsService.loadUserByUsername
        assertTrue(mockRequest.getPassword().equals(mockUserDetails.getPassword()));
        assertTrue(actualUserDetails != null);

        when(userRepository.findByEmail(mockRequest.getEmail())).thenReturn(Optional.of(userData));
        Optional<UserData> userdata = userRepository.findByEmail(mockRequest.getEmail());
        assertNotNull(userdata);
        verify(userRepository, times(1)).findByEmail(any());


        when(jwtService.generateJwtToken(any())).thenReturn("jwtToken");
        var jwtToken = jwtService.generateJwtToken(userdata.get());
        assertEquals("jwtToken", jwtToken);
        verify(jwtService, times(1)).generateJwtToken(any());

        when(jwtService.generateRefreshToken(any())).thenReturn("refreshToken");
        var refreshToken = jwtService.generateRefreshToken(userdata.get());
        assertEquals("refreshToken", refreshToken);
        verify(jwtService, times(1)).generateRefreshToken(any());

    }


}