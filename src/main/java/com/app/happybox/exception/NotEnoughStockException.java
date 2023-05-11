package com.app.happybox.exception;

import com.app.happybox.type.ErrorCode;

public class NotEnoughStockException extends RuntimeException {


    public NotEnoughStockException() {
//        super(ErrorCode.);
        super("상품 수량 부족.");
    }

    // 예외 생성자
    public NotEnoughStockException(String message) {
        super(message);
    }
}
