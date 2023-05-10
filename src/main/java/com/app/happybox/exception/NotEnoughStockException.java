package com.app.happybox.exception;

public class NotEnoughStockException extends RuntimeException {

    // 예외 생성자
    public NotEnoughStockException(String message) {
        super(message);
    }
}
