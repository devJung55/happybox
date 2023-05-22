package com.app.happybox.exception;

import com.app.happybox.type.ErrorCode;

public class NoticeNotFoundException extends RuntimeException {
    public NoticeNotFoundException() {
        super(ErrorCode.NOTICE_NOT_FOUND.getMessage());
    }

    public NoticeNotFoundException(String message) {
        super(message);
    }
}
