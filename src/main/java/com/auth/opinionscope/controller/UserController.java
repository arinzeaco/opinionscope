package com.auth.opinionscope.controller;

import com.auth.opinionscope.config.AuthenticationRequest;
import com.auth.opinionscope.model.auth.User;
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
    public ResponseEntity<?>  createUser(@Valid @RequestBody User user) {

        var checkIfUserAlreadyExist = userService.checkIfUserAlreadyExist(user);
        if (checkIfUserAlreadyExist) {
            Response response = new Response();
            response.setStatusCode("400");
            response.setStatusMsg("User Already exist");
            return ResponseEntity.ok(response);
        }
        var createUser = userService.createUser(user);
        Response response = new Response();
        response.setStatusCode("400");
        response.setStatusMsg("User Already exist");
        return ResponseEntity.ok(createUser);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?>  login(@Valid @RequestBody AuthenticationRequest request) {
        var authenticate = userService.authenticate(request);

        return authenticate;
    }

    @PostMapping(value = "/validateEmail")
    public ResponseEntity<?> validateEmail(@Valid @RequestBody AuthenticationRequest request) {

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

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User user = userService.findByUserId(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/profile_image")
    public ResponseEntity<User> uploadProfileImage(@RequestParam("userId") Long userId,
                                                   @RequestParam("image") MultipartFile imageFile) throws IOException {
        User user = userService.findByUserId(userId);
        if (user != null) {
            String uploadedFileName = imageService.uploadProfileImage(imageFile, userId.toString(), user.getEmail());

            if (user.getUsersDetails() == null) {
                UsersDetails userDetails = new UsersDetails();
                userDetails.setProfileImageUrl(uploadedFileName);
                user.setUsersDetails(userDetails);
            } else {
                user.getUsersDetails().setProfileImageUrl(uploadedFileName);
            }

            User savedUser = userService.updateUser(user);
            return ResponseEntity.ok(savedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
