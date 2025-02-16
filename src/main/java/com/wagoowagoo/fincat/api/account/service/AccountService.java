package com.wagoowagoo.fincat.api.account.service;

import com.wagoowagoo.fincat.api.account.dto.AccountDto;
import com.wagoowagoo.fincat.api.account.entity.Account;
import com.wagoowagoo.fincat.api.account.entity.AccountType;
import com.wagoowagoo.fincat.api.account.repository.AccountRepository;
import com.wagoowagoo.fincat.common.ErrorCode;
import com.wagoowagoo.fincat.config.SecurityAccount;
import com.wagoowagoo.fincat.exception.ApiException;
import com.wagoowagoo.fincat.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Account account = accountRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " 존재하지 않는 사용자명입니다."));
        return new SecurityAccount(account);
    }

    @Transactional
    public Account createAccount(AccountDto.CreateAccountWithEmail dto) {
        accountRepository.findByEmail(dto.getEmail()).ifPresent(account -> {
            throw new ApiException(ErrorCode.DUPLICATE_EMAIL_ACCOUNT);
        });
        return Account.builder()
                .accountType(AccountType.EMAIL)
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }

    public Account getAccount(String accessToken) {
        Long accountId = JwtUtil.extractAccountId(accessToken);
        return findAccountById(accountId);
    }

    private Account findAccountById(Long accountId) {
        return accountRepository
                .findById(accountId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_EMAIL_ACCOUNT));
    }

    public Account findAccountByEmail(String email) {
        return accountRepository
                .findByEmail(email)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_EMAIL_ACCOUNT));
    }

    @Transactional
    public void updateAccessToken(Account account, String accessToken) {
        account.updateAccessToken(accessToken);
    }
}
