package com.auth.opinionscope.dataModel.request;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

@Data
public class ChangePasswordModel {

    private String email;

    private String currentPassword;

    private String newPassword;


}
