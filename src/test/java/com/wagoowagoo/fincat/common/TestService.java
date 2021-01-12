package com.wagoowagoo.fincat.common;

import com.wagoowagoo.fincat.api.account.entity.Account;
import com.wagoowagoo.fincat.api.account.service.AccountService;
import com.wagoowagoo.fincat.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Autowired
    private AccountService accountService;

    public String getAccessToken() {
        UserDetails userDetails = accountService.loadUserByUsername("test1@test.com");
        Account account = accountService.findAccountByEmail("test1@test.com");
        return JwtUtil.generateToken(userDetails, account);
    }
}
