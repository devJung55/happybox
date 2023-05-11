package com.app.happybox.type;

public enum ErrorCode {
    /* ===== AUTH ===== */
    LOGIN_FAILED("AUTH_001", "LOGIN_FAILED.", 401),
    AUTHENTICATION_FAILED("AUTH_002", "AUTHENTICATION_FAILED", 401),
    /* ================ */
    /* ===== NOT_FOUND ===== */
    USER_NOT_FOUND("NOT_FOUND_001", "USER_NOT_FOUND", 404),
    PRODUCT_NOT_FOUND("NOT_FOUND_002", "PRODUCT_NOT_FOUND", 404),
    DONATION_BOARD_NOT_FOUNT("NOT_FOUND_003", "DONATION_BOARD_NOT_FOUND", 404);

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
