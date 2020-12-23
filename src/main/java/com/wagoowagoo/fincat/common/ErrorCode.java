package com.wagoowagoo.fincat.common;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // OPEN API [00xx]
    FINLIFE_API_ERROR("0001", "FINLIFE API 에러"),

    // ACCOUNT [01xx]
    LOGIN_FAIL("0101", "로그인 실패"),
    DUPLICATE_EMAIL_ACCOUNT("0102", "이미 존재하는 이메일 계정입니다."),
    NOT_FOUND_EMAIL_ACCOUNT("0103", "존재하지 않는 이메일 계정입니다."),

    // NOTICE [02xx]
    NOT_FOUND_NOTICE("0201", "공지사항을 찾을 수 없습니다."),

    // PRODUCT [03xx]
    PRODUCT_BOOKMARK_DELETE_ERROR("0301", "상품 북마크 삭제 오류가 발생했습니다."),

    // COMMON [99xx]
    REQUEST_ERROR("9901", "요청 에러"),
    PERMISSION_ERROR("9902", "권한 에러"),
    JWT_SIGNATURE_ERROR("9903", "토큰 서명 오류"),
    JWT_MALFORMED_ERROR("9904", "토큰 형식 오류"),
    JWT_EXPIRED_ERROR("9905", "토큰 만료"),
    INVALID_INPUT_VALUE("9906", "유효하지 않는 값입니다."),
    UNEXPECTED_ERROR("9999", "알 수 없는 에러")
    ;

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
