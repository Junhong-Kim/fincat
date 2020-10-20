package com.wagoowagoo.fincat.config;

import com.wagoowagoo.fincat.api.account.entity.Account;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;

public class SecurityAccount extends User {

    public SecurityAccount(Account account) {
        super(account.getEmail(), account.getPassword(), new ArrayList<>());
    }
}
