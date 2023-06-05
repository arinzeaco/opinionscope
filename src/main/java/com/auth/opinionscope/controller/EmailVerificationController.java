package com.auth.opinionscope.controller;

import com.auth.opinionscope.model.User;
import com.auth.opinionscope.rest.Response;
import com.auth.opinionscope.service.EmailVerificationService;
import com.auth.opinionscope.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequestMapping("api/v1/otp")
@RestController
public class EmailVerificationController {
    @Autowired
    EmailVerificationService emailVerificationService;

    @Autowired
    UserService userService;


    @PostMapping("/send")
    public Response sendVerificationEmail(@RequestParam("email") String email) {
        // Generate a random 6-digit OTP

        // Save the verification details in the database
        emailVerificationService.createVerification(email);

        // Send the email with the OTP to the user's email address
//        sendEmail(email, otp);


//        emailService.sendMail(user);
//        emailService.createVerificationToken(user);
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Otp Has been sent to you email");
        response.setData(true);

        return response;
    }



    @PostMapping("/validate_otp")
    public Response validateOTP(@RequestParam String email, @RequestParam String otp) {
         if(emailVerificationService.validateOTP(email, otp)) {
            if(userService.verifyEmail(email)){
                Response response = new Response();
                response.setStatusCode("200");
                response.setStatusMsg("Email Successfully verified");
                response.setData(true);
                return response;
            }

        }
        Response response = new Response();
        response.setStatusCode("400");
        response.setStatusMsg("OTP is not valid");
        response.setData(false);

        return response;
    }


}