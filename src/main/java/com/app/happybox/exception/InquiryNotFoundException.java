package com.app.happybox.exception;

import com.app.happybox.type.ErrorCode;

public class InquiryNotFoundException extends RuntimeException {
    public InquiryNotFoundException() {
        super(ErrorCode.INQUIRY_NOT_FOUND.getMessage());
    }

    public InquiryNotFoundException(String message) {
        super(message);
    }
}
