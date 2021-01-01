package com.wagoowagoo.fincat.api.account.controller;

import com.wagoowagoo.fincat.api.account.dto.AccountDto;
import com.wagoowagoo.fincat.api.account.dto.AccountResponse;
import com.wagoowagoo.fincat.api.account.entity.Account;
import com.wagoowagoo.fincat.api.account.repository.AccountRepository;
import com.wagoowagoo.fincat.api.account.service.AccountService;
import com.wagoowagoo.fincat.common.BaseResponse;
import com.wagoowagoo.fincat.common.ErrorCode;
import com.wagoowagoo.fincat.common.ErrorResponse;
import com.wagoowagoo.fincat.common.SuccessResponse;
import com.wagoowagoo.fincat.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "계정 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AuthenticationManager authenticationManager;
    private final AccountRepository accountRepository;
    private final AccountService accountService;

    @ApiOperation(value = "이메일 회원가입")
    @PostMapping("/email")
    public BaseResponse createAccount(@Valid @RequestBody AccountDto.CreateAccountWithEmail dto) {
        accountRepository.save(accountService.createAccount(dto));
        return new SuccessResponse<>();
    }

    @ApiOperation(value = "이메일 로그인")
    @PostMapping("/email/login")
    public BaseResponse loginAccount(@RequestBody AccountDto.LoginAccount dto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
        } catch (BadCredentialsException e) {
            return new ErrorResponse(ErrorCode.LOGIN_FAIL);
        }
        UserDetails userDetails = accountService.loadUserByUsername(dto.getEmail());
        Account account = accountService.findAccountByEmail(dto.getEmail());

        String accessToken = JwtUtil.generateToken(userDetails, account);
        accountService.updateAccessToken(account, accessToken);

        AccountResponse.LoginAccount response = new AccountResponse.LoginAccount(accessToken);
        return new SuccessResponse<>(response);
    }
}
