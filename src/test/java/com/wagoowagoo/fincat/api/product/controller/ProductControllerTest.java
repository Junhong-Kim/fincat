package com.wagoowagoo.fincat.api.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wagoowagoo.fincat.common.ControllerTest;
import com.wagoowagoo.fincat.common.TestService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

class ProductControllerTest extends ControllerTest {

    @Autowired
    private ProductController productController;

    @Autowired
    private TestService testService;

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
        ObjectNode content = objectMapper.createObjectNode();
        content.put("productType", "DEPOSIT");
        content.put("productCode", "1");
        content.put("finCompanyCode", "1");

        // when
        String url = "/api/v1/product/bookmark";

        // then
        MockHttpServletRequestBuilder requestBuilder = post(url)
                .header("Authorization", "Bearer " + testService.getAccessToken())
                .content(objectMapper.writeValueAsString(content))
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder);
    }

    @Test
    @DisplayName("상품 북마크 목록 조회 - 성공")
    void getProductBookmarkList_successTest() throws Exception {
        // given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", "0");
        params.add("size", "10");
        params.add("productType", "DEPOSIT");

        // when
        String url = "/api/v1/product/bookmark";

        MockHttpServletRequestBuilder requestBuilder = get(url)
                .header("Authorization", "Bearer " + testService.getAccessToken())
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
        Long bookmarkId = 1L;

        // when
        String url = String.join("/", "/api/v1/product/bookmark", String.valueOf(bookmarkId));

        // then
        MockHttpServletRequestBuilder requestBuilder = delete(url)
                .header("Authorization", "Bearer " + testService.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder);
    }
}
