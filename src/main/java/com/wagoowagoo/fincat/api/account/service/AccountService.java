package com.wagoowagoo.fincat.api.account.service;

import com.wagoowagoo.fincat.api.account.dto.AccountDto;
import com.wagoowagoo.fincat.api.account.entity.Account;
import com.wagoowagoo.fincat.api.account.repository.AccountRepository;
import com.wagoowagoo.fincat.config.SecurityAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " 존재하지 않는 사용자명입니다."));
        return new SecurityAccount(account);
    }

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
