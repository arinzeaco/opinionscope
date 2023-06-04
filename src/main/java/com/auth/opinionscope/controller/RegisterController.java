package com.auth.opinionscope.controller;

import com.auth.opinionscope.config.AuthenticationRequest;
import com.auth.opinionscope.model.User;
import com.auth.opinionscope.rest.Response;
import com.auth.opinionscope.service.EmailService;
import com.auth.opinionscope.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequestMapping("api/v1/auth")
@RestController
public class RegisterController {

    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;


    @PostMapping(value = "/createUser")
    public ResponseEntity<Response> createUser(@Valid @RequestBody User user) {

        var checkIfUserAlreadyExist = userService.checkIfUserAlreadyExist(user);
        if (checkIfUserAlreadyExist) {
            Response response = new Response();
            response.setStatusCode("400");
            response.setStatusMsg("User Already exist");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }

        var token = userService.createUser(user);

        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("User succesfully registered");
        response.setData(token);
//        emailService.sendMail(user);
//        emailService.createVerificationToken(user);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);

    }

    @PostMapping(value = "/login")
    public ResponseEntity<Response> getcontact(@Valid @RequestBody AuthenticationRequest request) {

        var token = userService.authenticate(request);

        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Message saved successfully");
        response.setData(token);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("isMsgSaved", "true")
                .body(response);
    }

    @PostMapping(value = "/validateEmail")
    public ResponseEntity<Response> validateEmail(@Valid @RequestBody AuthenticationRequest request) {

        var token = userService.authenticate(request);

        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Message saved successfully");
        response.setData(token);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("isMsgSaved", "true")
                .body(response);
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<Response> logout(@Valid @RequestBody AuthenticationRequest request) {

        var token = userService.authenticate(request);

        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Message saved successfully");
        response.setData(token);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("isMsgSaved", "true")
                .body(response);
    }



}
