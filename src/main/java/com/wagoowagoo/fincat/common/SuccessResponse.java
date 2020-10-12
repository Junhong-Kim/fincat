package com.wagoowagoo.fincat.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SuccessResponse<T> extends BaseResponse implements Serializable {

    private T data; // API 응답 데이터

    public SuccessResponse(T data) {
        this.setResult(true);
        this.setData(data);
    }
}
