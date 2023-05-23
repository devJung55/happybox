package com.app.happybox.type;

import io.lettuce.core.cluster.event.RedirectionEventSupport;
import lombok.Getter;

import java.util.logging.LogRecord;

@Getter
public enum ErrorCode {
    /* ===== AUTH ===== */
    LOGIN_FAILED("AUTH_001", "LOGIN_FAILED.", 401),
    AUTHENTICATION_FAILED("AUTH_002", "AUTHENTICATION_FAILED", 401),
    /* ================ */
    /* ===== NOT_FOUND ===== */
    USER_NOT_FOUND("NOT_FOUND_001", "USER_NOT_FOUND", 404),
    PRODUCT_NOT_FOUND("NOT_FOUND_002", "PRODUCT_NOT_FOUND", 404),
    BOARD_NOT_FOUNT("NOT_FOUND_003", "BOARD_NOT_FOUND", 404),
    CART_NOT_FOUND("NOT_FOUND_004", "CART_NOT_FOUND", 404),
    SUBSCRIPTION_NOT_FOUND("NOT_FOUND_005", "SUBSCRIPTION_NOT_FOUND", 404),
    LIKE_NOT_FOUND("NOT_FOUND_006", "LIKE_NOT_FOUND", 404),
    REPLY_NOT_FOUND("NOT_FOUND_006", "REPLY_NOT_FOUND", 404),
    CHAT_ROOM_NOT_FOUND("NOT_FOUND_007", "CHAT_ROOM_NOT_FOUND", 404),
    NOTICE_NOT_FOUND("NOT_FOUND_008", "NOTICE_NOT_FOUND", 404),
    INQUIRY_NOT_FOUND("NOT_FOUND_009", "INQUIRY_NOT_FOUND", 404),
    FOODCALENDAR_NOT_FOUND("NOT_FOUND_010","FOODCALENDAR_NOT_FOUND",404);

    private final String code;
    private final String message;
    private final int status;

    //    필드 초기화
    ErrorCode(final String code, final String message, final int status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
