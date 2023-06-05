package com.auth.opinionscope.controller;

import com.auth.opinionscope.dataModel.ChangePasswordModel;
import com.auth.opinionscope.dataModel.ForgotPasswordModel;
import com.auth.opinionscope.rest.Response;
import com.auth.opinionscope.service.EmailVerificationService;
import com.auth.opinionscope.service.ForgotPasswordService;
import com.auth.opinionscope.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequestMapping("api/v1/password")
@RestController
public class ForgotPasswordController {
    @Autowired
    EmailVerificationService emailVerificationService;

    @Autowired
    ForgotPasswordService forgotPasswordService;

    @Autowired
    UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/send_mail")
    public Response sendMail(@RequestParam String email) {
        emailVerificationService.createVerification(email);
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Otp has been sent to you email");
        response.setData(true);

        return response;
    }
    private String generateOTP() {
        // Logic to generate a random 6-digit OTP
        // Implement your own logic or use a library for generating random OTPs
        // Example:
        int minOTPValue = 100000;
        int maxOTPValue = 999999;
        int otpValue = (int) (Math.random() * (maxOTPValue - minOTPValue + 1) + minOTPValue);
        return String.valueOf(otpValue);
    }

    @PostMapping("/forgot_password")
    public Response forgotPassword(@Valid @RequestBody ForgotPasswordModel forgotPasswordModel) {
         if(emailVerificationService.validateOTP(forgotPasswordModel.getEmail(), forgotPasswordModel.getOpt())) {
             forgotPasswordService.forgotPassword(forgotPasswordModel);
        }
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Email has been verified");
        response.setData(true);
        return response;
    }


    @PostMapping("/change_password")
    public Response changePassword(@Valid @RequestBody ChangePasswordModel changePasswordModel){
         forgotPasswordService.changePassword(changePasswordModel);
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Password Changed");
        response.setData(true);

        return response;
    }

}