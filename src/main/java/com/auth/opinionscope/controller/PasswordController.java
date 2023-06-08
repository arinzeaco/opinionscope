package com.auth.opinionscope.controller;

import com.auth.opinionscope.dataModel.request.ChangePasswordModel;
import com.auth.opinionscope.dataModel.request.ForgotPasswordModel;
import com.auth.opinionscope.rest.Response;
import com.auth.opinionscope.service.EmailVerificationService;
import com.auth.opinionscope.service.PasswordService;
import com.auth.opinionscope.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequestMapping("api/v1/password")
@RestController
public class PasswordController {
    @Autowired
    EmailVerificationService emailVerificationService;

    @Autowired
    PasswordService passwordService;

    @Autowired
    UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/send_otp")
    public Response sendMail(@RequestParam String email) {
        emailVerificationService.createVerification(email);
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Otp has been sent to you email");
        response.setData(true);

        return response;
    }


    @PostMapping("/forgot_password")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordModel forgotPasswordModel) {

        if(emailVerificationService.validateOTP(forgotPasswordModel.getEmail(), forgotPasswordModel.getOtp())) {
            if(passwordService.forgotPassword(forgotPasswordModel)){
                Response response = new Response();
                response.setStatusCode("200");
                response.setStatusMsg("Password Updated");
                response.setData(true);
                return ResponseEntity.ok(response);

            }

        }
        Response response = new Response();
        response.setStatusCode("400");
        response.setStatusMsg("OTP is not valid");
        response.setData(false);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/change_password")
    public Response changePassword(@Valid @RequestBody ChangePasswordModel changePasswordModel){


        passwordService.changePassword(changePasswordModel);
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Password Changed");
        response.setData(true);

        return response;
    }
}