package com.wagoowagoo.fincat.common;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // OPEN API [00xx]
    FINLIFE_API_ERROR("0001", "FINLIFE API 에러"),

    // ACCOUNT [01xx]
    LOGIN_FAIL("0101", "로그인 실패"),

    // NOTICE [02xx]
    NOT_FOUND_NOTICE("0201", "공지사항을 찾을 수 없습니다."),

    // COMMON [99xx]
    REQUEST_ERROR("9901", "요청 에러"),
    UNEXPECTED_ERROR("9999", "알 수 없는 에러")
    ;

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
