package com.app.happybox.exception;

import com.app.happybox.type.ErrorCode;

public class LikeNotFoundException extends RuntimeException {

    public LikeNotFoundException() {
        super(ErrorCode.LIKE_NOT_FOUND.getMessage());
    }

    public LikeNotFoundException(String message) {
        super(message);
    }
}
