package com.auth.opinionscope.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtWithResponse {

    private String statusCode;
    private String statusMsg;
    private Object data;
    private String access_token;
    private String refresh_token;

}

