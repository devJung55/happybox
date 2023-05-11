package com.app.happybox.exception;

import com.app.happybox.type.ErrorCode;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND.name());
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
