package com.wagoowagoo.fincat.api.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class AccountResponse implements Serializable {

    private static final long serialVersionUID = 951467591310655577L;

    private AccountResponse() {
        throw new IllegalStateException("DTO 클래스는 인스턴스 생성이 금지되어 있습니다.");
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class LoginAccount implements Serializable {

        private static final long serialVersionUID = -7482246639370754365L;

        private String accessToken;
    }
}
