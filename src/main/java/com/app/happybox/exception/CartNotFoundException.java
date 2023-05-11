package com.app.happybox.exception;

import com.app.happybox.type.ErrorCode;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException() {
        super(ErrorCode.CART_NOT_FOUND.getMessage());
    }

    public CartNotFoundException(String message) {
        super(message);
    }
}
