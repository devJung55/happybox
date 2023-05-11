package com.app.happybox.exception;


import com.app.happybox.type.ErrorCode;

public class LoginFailedException extends RuntimeException{
    public LoginFailedException() {
        super(ErrorCode.LOGIN_FAILED.getMessage());
    }

    public LoginFailedException(String message) {
        super(message);
    }
}
