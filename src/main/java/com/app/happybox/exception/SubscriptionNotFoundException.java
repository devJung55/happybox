package com.app.happybox.exception;

import com.app.happybox.type.ErrorCode;

public class SubscriptionNotFoundException extends RuntimeException {

    public SubscriptionNotFoundException() {
        super(ErrorCode.SUBSCRIPTION_NOT_FOUND.getMessage());
    }

    public SubscriptionNotFoundException(String message) {
        super(message);
    }
}
