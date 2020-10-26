package com.wagoowagoo.fincat.api.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

public class AccountDto {

    private AccountDto() {
        throw new IllegalStateException("DTO 클래스는 인스턴스 생성이 금지되어 있습니다.");
    }

    @Getter
    @Setter
    @Builder
    public static class CreateAccountWithEmail {

        @NotBlank
        private String email; // TODO: 이메일 유효성 검사

        @NotBlank
        private String password;
    }

    @Getter
    @Setter
    public static class LoginAccount {
        private String email;
        private String password;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class LoginResponse {
        private String accessToken;
    }
}
