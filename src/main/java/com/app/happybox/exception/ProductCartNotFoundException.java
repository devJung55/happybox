package com.app.happybox.exception;

import com.app.happybox.type.ErrorCode;

public class ProductCartNotFoundException extends RuntimeException {
    public ProductCartNotFoundException() {
        super(ErrorCode.CART_NOT_FOUND.getMessage());
    }

    public ProductCartNotFoundException(String message) {
        super(message);
    }
}
