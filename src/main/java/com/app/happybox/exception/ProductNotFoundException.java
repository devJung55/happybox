package com.app.happybox.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException() {
        super(ErrorCode.PRODUCT_NOT_FOUND.name());
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
