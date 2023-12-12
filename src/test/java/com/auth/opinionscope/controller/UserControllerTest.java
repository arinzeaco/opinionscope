package com.auth.opinionscope.controller;

import com.auth.opinionscope.config.AuthenticationRequest;
import com.auth.opinionscope.rest.GlobalExceptionRestController;
import com.auth.opinionscope.rest.JwtWithResponse;
import com.auth.opinionscope.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;




class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
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
                .andExpect(jsonPath("$.data").isArray());
    }




    @DisplayName("Test for successful login")
    @Test
    public void shouldLogin() throws Exception {
        // Create a mock AuthenticationRequest object

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", "mbhjb@gmao.com");
        jsonObject.put("password", "12345678");

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andExpect(status().isOk());
    }

    @DisplayName("Test for error login")
    @Test
    public void testFailedLogin() throws Exception {
        // Prepare a mock AuthenticationRequest with invalid credentials
        AuthenticationRequest invalidRequest = new AuthenticationRequest();
        invalidRequest.setEmail("invalid@example.com");
        invalidRequest.setPassword("wrongPassword");
        JwtWithResponse response = new JwtWithResponse();
        response.setStatusCode("400");
        response.setStatusMsg("User not found");
        // Mock the userService.authenticate method to return a failure response
        when(userService.authenticate(any(AuthenticationRequest.class)))
                .thenReturn(response);

        // Perform the POST request to the /login endpoint with the invalid credentials
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invalidRequest)))
                .andExpect(status().isNotFound());
        // Add additional assertions as needed
    }

    // Utility method to convert objects to JSON string
    private String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

}