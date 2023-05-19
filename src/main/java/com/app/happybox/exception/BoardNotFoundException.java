package com.app.happybox.exception;

import com.app.happybox.type.ErrorCode;

public class BoardNotFoundException extends RuntimeException{

    public BoardNotFoundException() {
        super(ErrorCode.BOARD_NOT_FOUNT.name());
    }

    public BoardNotFoundException(String message){
        super(message);
    }
}
