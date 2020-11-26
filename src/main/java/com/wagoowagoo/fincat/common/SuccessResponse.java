package com.wagoowagoo.fincat.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class SuccessResponse<T> extends BaseResponse implements Serializable {

    private static final long serialVersionUID = 1921298826463618980L;

    private T data; // API 응답 데이터

    public SuccessResponse() {
        this.setResult(true);
    }

    public SuccessResponse(T data) {
        this();
        this.setData(data);
    }
}
