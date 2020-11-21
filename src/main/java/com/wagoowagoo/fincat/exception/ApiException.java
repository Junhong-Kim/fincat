package com.wagoowagoo.fincat.exception;

import com.wagoowagoo.fincat.common.ErrorCode;

public class ApiException extends RuntimeException {

    public ApiException(ErrorCode errorCode) {
        super(errorCode.name());
    }
}
