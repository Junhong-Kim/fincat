package com.wagoowagoo.fincat.common;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // OPEN API
    FINLIFE_API_ERROR("0001", "FINLIFE API 에러"),

    UNEXPECTED_ERROR("9999", "알 수 없는 에러");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
