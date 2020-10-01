package com.wagoowagoo.fincat.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse extends BaseResponse {

    private String code; // 에러 코드
    private String message; // 에러 메시지

    public ErrorResponse(ErrorCode errorCode) {
        this.setResult(false);
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
}
