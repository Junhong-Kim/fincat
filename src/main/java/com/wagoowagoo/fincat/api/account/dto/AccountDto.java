package com.wagoowagoo.fincat.api.account.dto;

import com.wagoowagoo.fincat.util.RegxUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class AccountDto {

    private AccountDto() {
        throw new IllegalStateException("DTO 클래스는 인스턴스 생성이 금지되어 있습니다.");
    }

    @Getter
    @Setter
    @Builder
    public static class CreateAccountWithEmail {

        @NotBlank
        @Pattern(regexp = RegxUtil.EMAIL)
        private String email;

        @NotBlank
        private String password;
    }

    @Getter
    @Setter
    public static class LoginAccount {

        private String email;
        private String password;
    }
}
