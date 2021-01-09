package com.wagoowagoo.fincat.api.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wagoowagoo.fincat.common.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

class AccountControllerTest extends ControllerTest {

    @Autowired
    private AccountController accountController;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected Object controller() {
        return accountController;
    }

    @Test
    @DisplayName("이메일 계정 생성 - 성공")
    void createEmailAccount_successTest() throws Exception {
        // given
        ObjectNode content = objectMapper.createObjectNode();
        content.put("email", "test@test.com");
        content.put("password", "test");

        // when
        String url = "/api/v1/account/email";

        // then
        MockHttpServletRequestBuilder requestBuilder = post(url)
                .content(objectMapper.writeValueAsString(content))
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder);
    }

    @Test
    @DisplayName("이메일 로그인 - 성공")
    void loginEmailAccount_successTest() throws Exception {
        // given
        ObjectNode content = objectMapper.createObjectNode();
        content.put("email", "test1@test.com");
        content.put("password", "test");

        // when
        String url = "/api/v1/account/email/login";

        // then
        MockHttpServletRequestBuilder requestBuilder = post(url)
                .content(objectMapper.writeValueAsString(content))
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder);
    }
}
