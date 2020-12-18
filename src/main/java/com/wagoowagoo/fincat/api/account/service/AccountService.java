package com.wagoowagoo.fincat.api.account.service;

import com.wagoowagoo.fincat.api.account.dto.AccountDto;
import com.wagoowagoo.fincat.api.account.entity.Account;
import com.wagoowagoo.fincat.api.account.entity.AccountType;
import com.wagoowagoo.fincat.api.account.repository.AccountRepository;
import com.wagoowagoo.fincat.common.ErrorCode;
import com.wagoowagoo.fincat.config.SecurityAccount;
import com.wagoowagoo.fincat.exception.ApiException;
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
     * 이메일 계성 생성
     * @param dto 이메일 계성 생성 정보
     */
    public Account createAccount(AccountDto.CreateAccountWithEmail dto) {
        accountRepository.findByEmail(dto.getEmail()).ifPresent(account -> {
            throw new ApiException(ErrorCode.DUPLICATE_EMAIL_ACCOUNT);
        });
        return Account.builder()
                .accountType(AccountType.EMAIL.getValue())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }

    public Account getAccountByEmail(String username) {
        return accountRepository
                .findByEmail(username)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_EMAIL_ACCOUNT));
    }
}
