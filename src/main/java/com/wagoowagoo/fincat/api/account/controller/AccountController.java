package com.wagoowagoo.fincat.api.account.controller;

import com.wagoowagoo.fincat.api.account.dto.AccountDto;
import com.wagoowagoo.fincat.api.account.entity.Account;
import com.wagoowagoo.fincat.api.account.repository.AccountRepository;
import com.wagoowagoo.fincat.api.account.service.AccountService;
import com.wagoowagoo.fincat.common.BaseResponse;
import com.wagoowagoo.fincat.common.ErrorCode;
import com.wagoowagoo.fincat.common.ErrorResponse;
import com.wagoowagoo.fincat.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final AuthenticationManager authenticationManager;
    private final AccountRepository accountRepository;
    private final AccountService accountService;

    @PostMapping
    public BaseResponse createAccount(@RequestBody AccountDto.CreateAccount dto) {
        Account account = accountService.createAccount(dto);
        accountRepository.save(account);
        return new SuccessResponse<>();
    }

    @PostMapping("/login")
    public BaseResponse loginAccount(@RequestBody AccountDto.LoginAccount dto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
        } catch (BadCredentialsException e) {
            return new ErrorResponse(ErrorCode.LOGIN_FAIL);
        }
        accountService.loadUserByUsername(dto.getEmail());
        return new SuccessResponse<>();
    }
}
