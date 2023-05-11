package com.app.happybox.exception;

import com.app.happybox.type.ErrorCode;

public class UserRoleUnsuitableException extends RuntimeException {
    public UserRoleUnsuitableException() {
        super(ErrorCode.AUTHENTICATION_FAILED.getMessage());
    }

    public UserRoleUnsuitableException(String message) {
        super(message);
    }
}
