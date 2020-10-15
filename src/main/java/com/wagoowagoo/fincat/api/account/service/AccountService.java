package com.wagoowagoo.fincat.api.account.service;

import com.wagoowagoo.fincat.api.account.dto.AccountDto;
import com.wagoowagoo.fincat.api.account.entity.Account;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    /***
     * 사용자 등록
     * @param dto 사용자 생성 정보
     */
    public Account createAccount(AccountDto.CreateAccount dto) {
        return Account.builder()
                .accountType(dto.getAccountType().toString())
                .email(dto.getEmail())
                .accessToken("accessToken")
                .build();
    }
}
