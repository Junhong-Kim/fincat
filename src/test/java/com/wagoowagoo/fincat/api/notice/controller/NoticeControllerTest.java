package com.wagoowagoo.fincat.api.notice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wagoowagoo.fincat.api.account.entity.Account;
import com.wagoowagoo.fincat.api.account.service.AccountService;
import com.wagoowagoo.fincat.common.ControllerTest;
import com.wagoowagoo.fincat.util.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

class NoticeControllerTest extends ControllerTest {

    @Autowired
    private NoticeController noticeController;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected Object controller() {
        return noticeController;
    }

    @Test
    @DisplayName("공지사항 생성 - 성공")
    void createNotice_successTest() throws Exception {
        // given
        ObjectNode content = objectMapper.createObjectNode();
        content.put("title", "test");
        content.put("contents", "test");
        content.put("createdBy", "test");
        content.put("updatedBy", "test");

        // when
        String url = "/api/v1/notice";

        // then
        MockHttpServletRequestBuilder requestBuilder = post(url)
                .header("Authorization", "Bearer " + getAccessToken())
                .content(objectMapper.writeValueAsString(content))
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder);
    }

    @Test
    @DisplayName("공지사항 목록 조회 - 성공")
    void getNoticeList_successTest() throws Exception {
        // given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", "0");
        params.add("size", "10");

        // when
        String url = "/api/v1/notice";

        // then
        MockHttpServletRequestBuilder requestBuilder = get(url)
                .params(params)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder);
    }

    @Test
    @DisplayName("공지사항 단건 조회 - 성공")
    void getNotice_successTest() throws Exception {
        // given
        Long noticeId = 1L;

        // when
        String url = String.join("/", "/api/v1/notice", String.valueOf(noticeId));

        // then
        MockHttpServletRequestBuilder requestBuilder = get(url)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder);
    }

    // FIXME: accessToken 생성 로직 분리
    private String getAccessToken() {
        UserDetails userDetails = accountService.loadUserByUsername("test1@test.com");
        Account account = accountService.findAccountByEmail("test1@test.com");
        return JwtUtil.generateToken(userDetails, account);
    }
}
