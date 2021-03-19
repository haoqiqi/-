package com.prodemo01.datatype.enums;

public enum ResponseCode {
    SUCCESS(200, "success"),
    FAILED(10, "failed"),
    ;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private final int code;
    private final String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
