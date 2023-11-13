package com.auth.opinionscope.controller;

import com.auth.opinionscope.model.auth.UserData;
import com.auth.opinionscope.model.Role;
import com.auth.opinionscope.rest.GlobalExceptionRestController;
import com.auth.opinionscope.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.sql.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;




class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final MockMvc mockMvc;

    public UserControllerTest() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new GlobalExceptionRestController())
                .build();
    }

    @DisplayName("Register login works with right input")
    @Test
    void createUserValidationSuccess() throws Exception {
//        api: GET /api/products/id/1 ==> 200 : ProductResponseDTO
        String requestBody = "{" +
                "\"email\": \"hfgb@yopmail.com\"," +
                "\"password\": \"12345678\"," +
                "\"mobile_number\": \"1234567890\"," +
                "\"country\": \"USA\"," +
                "\"firstname\": \"John\"," +
                "\"lastname\": \"Doe\"," +
                "\"dateOfBirth\": \"1990-01-01\"," +
                "\"googleAuthId\": \"google-auth-id\"," +
                "\"googleAuthSecret\": \"google-auth-secret\"," +
                "\"email_verified\": false," +
                "\"mobile_number_verified\": false," +
                "\"role\": \"ADMIN\"" +
                "}";

        mockMvc.perform(post("/api/v1/auth/createUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @DisplayName("Error if invalid input is anywhere")
    @Test
    public void createUserValidationFailure() throws Exception {

        String requestBody = "{" +
                "\"email\": \"\"," +
                "\"password\": \"12345678\"," +
                "\"mobile_number\": \"1234567890\"," +
                "\"country\": \"USA\"," +
                "\"firstname\": \"John\"," +
                "\"lastname\": \"Doe\"," +
                "\"dateOfBirth\": \"1990-01-01\"," +
                "\"googleAuthId\": \"google-auth-id\"," +
                "\"googleAuthSecret\": \"google-auth-secret\"," +
                "\"email_verified\": false," +
                "\"mobile_number_verified\": false," +
                "\"role\": \"ADMIN\"" +
                "}";

        mockMvc.perform(post("/api/v1/auth/createUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value("400"))
                .andExpect(jsonPath("$.statusMsg").value("Validation Failed"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0]").value("This field cannot be empty"));
    }




    @DisplayName("Test for successful login")
    @Test
    void login() {
        String requestBody = "{" +
                "\"email\": \"\"," +
                "\"password\": \"12345678\"," +
                "}";

    }

    @Test
    void validateEmail() {
    }

    @Test
    void logout() {
    }

    @Test
    void getUserById() {
    }

    @Test
    void uploadProfileImage() {
    }
}