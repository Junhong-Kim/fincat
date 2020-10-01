package com.wagoowagoo.fincat.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuccessResponse<T> extends BaseResponse {

    private T data; // API 응답 데이터

    public SuccessResponse(T data) {
        this.setResult(true);
        this.setData(data);
    }
}
