package com.hardcode.authserver.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UsernameNotFoundException extends Exception {
    private HttpStatus httpStatus;

    public UsernameNotFoundException(String msg, HttpStatus httpStatus) {
        super(msg);
        this.httpStatus = httpStatus;
    }

    public UsernameNotFoundException(String msg) {
        super(msg);
    }
}
