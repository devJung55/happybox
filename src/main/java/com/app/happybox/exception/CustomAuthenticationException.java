package com.app.happybox.exception;

import com.app.happybox.type.ErrorCode;

public class CustomAuthenticationException extends RuntimeException{
    public CustomAuthenticationException() {
        super(ErrorCode.AUTHENTICATION_FAILED.getMessage());
    }

    public CustomAuthenticationException(String message) {
        super(message);
    }
}
