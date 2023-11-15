package com.auth.opinionscope.controller;

import com.auth.opinionscope.model.auth.UserData;
import com.auth.opinionscope.repository.UserRepository;
import com.auth.opinionscope.rest.Response;
import com.auth.opinionscope.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RequestMapping("api/v1/profile")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ProfileController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository usersRepository;

    @GetMapping("/get_profile/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        Optional<UserData>  userData = usersRepository.findByUserId(userId);

        if (userData.isPresent()) {
            Response response = new Response();
            response.setStatusCode("200");
            response.setStatusMsg("User successfully registered");
            response.setData(userData.get());
            return ResponseEntity.ok(response);
        }

        Response response = new Response();
        response.setStatusCode("400");
        response.setStatusMsg("User N0t available");
        response.setData(userData.get());
        return ResponseEntity.badRequest().body(response);

    }
}
