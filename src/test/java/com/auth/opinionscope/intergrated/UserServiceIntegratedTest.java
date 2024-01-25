package com.auth.opinionscope.intergrated;

import com.auth.opinionscope.config.AuthenticationRequest;
import com.auth.opinionscope.model.auth.UserData;
import com.auth.opinionscope.repository.TokenRepository;
import com.auth.opinionscope.repository.UserRepository;
import com.auth.opinionscope.rest.JwtWithResponse;
import com.auth.opinionscope.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

import static com.auth.opinionscope.model.Role.USERS;
import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class UserServiceIntegratedTest  {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Test
    @Transactional
    public void createUser() {
        // Create a real userData object
        UserData userData = new UserData();
        userData.setEmail("existjing@example.com");
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


        // Call the real service method
        JwtWithResponse response = userService.createUser(userData);

        assertNotNull(response);
        assertEquals("200", response.getStatusCode());
        assertEquals("User successfully registered", response.getStatusMsg());
        assertEquals(userData, response.getData());
        assertNotNull(response.getAccess_token());
        assertNotNull(response.getRefresh_token());

    }

    @Test
    @Sql("classpath:insert_user.sql")
    @Transactional
    void testLoginSuccess() {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("userone@example.com");
        request.setPassword("password123");
        JwtWithResponse response = userService.authenticate(request);
        // Then
        assertNotNull(response);
        assertEquals("200", response.getStatusCode());
        assertEquals("User successfully LoggedIn", response.getStatusMsg());
        assertNotNull(response.getData());
        assertNotNull(response.getAccess_token());
        assertNotNull(response.getRefresh_token());
    }

    @Test
    void testLoginFail() {
        // When
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("userone@example.com");
        request.setPassword("wrongpassword");

        JwtWithResponse response = userService.authenticate(request);

        // Then
        assertNotNull(response);
        assertEquals("400", response.getStatusCode());
        assertEquals("User not found", response.getStatusMsg());
    }

    @Test
    @Sql("classpath:insert_user.sql")
    @Transactional
    void testCheckIfUserAlreadyExist() {
        UserData existingUser = new UserData();
        existingUser.setEmail("userone@example.com");
        assertTrue(userRepository.existsByEmail(existingUser.getEmail()));
    }

    @Test
    @Sql("classpath:insert_user.sql")
    @Transactional
    void testCheckIfUserDoesNotExist() {
        UserData existingUser = new UserData();
        existingUser.setEmail("usehr@example.com");

        assertFalse(userService.checkIfUserAlreadyExist(existingUser));

        assertFalse(userRepository.existsByEmail(existingUser.getEmail()));
    }


}