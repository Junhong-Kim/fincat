package com.wagoowagoo.fincat.api.account.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountType {

    EMAIL("EMAIL", "이메일 계정"),
    KAKAO("KAKAO", "카카오 계정"),
    FACEBOOK("FACEBOOK", "페이스북 계정"),
    GOOGLE("GOOGLE", "구글 계정");

    private final String value;
    private final String description;
}
