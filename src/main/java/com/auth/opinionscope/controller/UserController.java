package com.auth.opinionscope.controller;

import com.auth.opinionscope.config.AuthenticationRequest;
import com.auth.opinionscope.model.auth.UserData;
import com.auth.opinionscope.model.auth.UsersDetails;
import com.auth.opinionscope.rest.Response;
import com.auth.opinionscope.service.EmailVerificationService;
import com.auth.opinionscope.service.ImageService;
import com.auth.opinionscope.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;


@Slf4j
@RequestMapping("api/v1/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ImageService imageService;
    //
    @Autowired
    EmailVerificationService emailVerificationService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping(value = "/createUser")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserData UserData) {

        var checkIfUserAlreadyExist = userService.checkIfUserAlreadyExist(UserData);
        if (checkIfUserAlreadyExist) {
            Response response = new Response();
            response.setStatusCode("400");
            response.setStatusMsg("User Already exist");
            return ResponseEntity.badRequest().body(response);
        }
        var createUser = userService.createUser(UserData);
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("User Create Successfully");
        return ResponseEntity.ok(createUser);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthenticationRequest request) {

        var authenticate = userService.authenticate(request);

        return ResponseEntity.ok(authenticate);
    }




}
