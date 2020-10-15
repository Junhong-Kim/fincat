package com.wagoowagoo.fincat.api.account.dto;

import com.wagoowagoo.fincat.api.account.entity.AccountType;
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
}
