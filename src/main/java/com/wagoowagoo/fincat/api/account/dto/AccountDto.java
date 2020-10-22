package com.wagoowagoo.fincat.api.account.dto;

import com.wagoowagoo.fincat.api.account.entity.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class AccountDto {

    @Getter
    @Setter
    @Builder
    public static class CreateAccount {
        private AccountType accountType;
        private String email;
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
