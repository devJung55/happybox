package com.app.happybox.exception;

import com.app.happybox.type.ErrorCode;

public class ReplyNotFoundException extends RuntimeException {
    public ReplyNotFoundException() {
        super(ErrorCode.REPLY_NOT_FOUND.getMessage());
    }
}
