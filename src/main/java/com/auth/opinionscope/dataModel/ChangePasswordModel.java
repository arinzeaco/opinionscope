package com.auth.opinionscope.dataModel;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

@Data
public class ChangePasswordModel {

    private String email;

    private String currentPassword;

    private String newPassword;


}
