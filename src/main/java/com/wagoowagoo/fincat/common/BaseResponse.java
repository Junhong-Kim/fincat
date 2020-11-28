package com.wagoowagoo.fincat.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BaseResponse implements Serializable {

    private static final long serialVersionUID = -2410671938451646855L;

    private boolean result; // API 성공여부
}
