package com.auth.opinionscope.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Throwable throwable) {
        super(throwable);
    }
    public UserNotFoundException( String msg, Throwable throwable) {
        super(msg, throwable);
    }
    public UserNotFoundException( String msg) {
        super(msg);
    }

}