package com.auth.opinionscope.dataModel.request;

import lombok.Data;

@Data
public class ForgotPasswordModel {

    private String email;

    private String otp;

    private String password;

}
