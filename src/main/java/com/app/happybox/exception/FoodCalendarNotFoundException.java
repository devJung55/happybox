package com.app.happybox.exception;

import com.app.happybox.type.ErrorCode;

public class FoodCalendarNotFoundException extends RuntimeException{

    public FoodCalendarNotFoundException() {
        super(ErrorCode.FOODCALENDAR_NOT_FOUND.name());
    }

    public FoodCalendarNotFoundException(String message){
        super(message);
    }
}
