package com.wagoowagoo.fincat.api.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wagoowagoo.fincat.api.account.entity.Account;
import com.wagoowagoo.fincat.api.account.service.AccountService;
import com.wagoowagoo.fincat.common.ControllerTest;
import com.wagoowagoo.fincat.util.JwtUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

class ProductControllerTest extends ControllerTest {

    @Autowired
    private ProductController productController;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected Object controller() {
        return productController;
    }

    @Test
    @DisplayName("상품 북마크 생성 - 성공")
    void createProductBookmark_successTest() throws Exception {
        // given
        String url = "/api/v1/product/bookmark";

        ObjectNode content = objectMapper.createObjectNode();
        content.put("productType", "DEPOSIT");
        content.put("productCode", "1");
        content.put("finCompanyCode", "1");

        // when
        MockHttpServletRequestBuilder requestBuilder = post(url)
                .header("Authorization", "Bearer " + getAccessToken())
                .content(objectMapper.writeValueAsString(content))
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(requestBuilder);
    }

    @Test
    @DisplayName("상품 북마크 목록 조회 - 성공")
    void getProductBookmarkList_successTest() throws Exception {
        // given
        String url = "/api/v1/product/bookmark";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", "0");
        params.add("size", "10");
        params.add("productType", "DEPOSIT");

        // when
        MockHttpServletRequestBuilder requestBuilder = get(url)
                .header("Authorization", "Bearer " + getAccessToken())
                .params(params)
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(requestBuilder);
    }

    @Disabled
    @Test
    @DisplayName("상품 북마크 삭제 - 성공")
    void deleteProductBookmark_successTest() throws Exception {
        // given
        String url = "/api/v1/product/bookmark/1";

        // when
        MockHttpServletRequestBuilder requestBuilder = delete(url)
                .header("Authorization", "Bearer " + getAccessToken())
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(requestBuilder);
    }

    // FIXME: accessToken 생성 로직 분리
    private String getAccessToken() {
        UserDetails userDetails = accountService.loadUserByUsername("test1@test.com");
        Account account = accountService.findAccountByEmail("test1@test.com");
        return JwtUtil.generateToken(userDetails, account);
    }
}
