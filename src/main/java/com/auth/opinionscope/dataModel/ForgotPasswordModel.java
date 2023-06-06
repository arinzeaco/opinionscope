package com.auth.opinionscope.dataModel;

import lombok.Data;

@Data
public class ForgotPasswordModel {

    private String email;

    private String otp;

    private String password;

}
