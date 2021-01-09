package com.wagoowagoo.fincat.api.finlife.controller;

import com.wagoowagoo.fincat.common.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class FinlifeControllerTest extends ControllerTest {

    @Autowired
    private FinlifeController finlifeController;

    @Override
    protected Object controller() {
        return finlifeController;
    }

    @Test
    @DisplayName("금융회사 목록 조회 (필수 값) - 성공")
    void getFinanceCompany_successTest() throws Exception {
        // given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("topFinGrpNo", "020000");
        params.add("pageNo", "1");

        // when
        String url = "/api/v1/finlife/financeCompany";

        // then
        MockHttpServletRequestBuilder requestBuilder = get(url)
                .params(params)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder);
    }

    @Test
    @DisplayName("예금상품 목록 조회 (필수 값) - 성공")
    void getDepositProductList_successTest() throws Exception {
        // given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("topFinGrpNo", "020000");
        params.add("saveTermList", "12");
        params.add("interestRateTypeList", "S");

        // when
        String url = "/api/v1/finlife/depositProduct";

        // then
        MockHttpServletRequestBuilder requestBuilder = get(url)
                .params(params)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder);
    }

    @Test
    @DisplayName("적금상품 목록 조회 (필수 값) - 성공")
    void getSavingProductList_successTest() throws Exception {
        // given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("topFinGrpNo", "020000");
        params.add("saveTermList", "12");
        params.add("interestRateTypeList", "S");

        // when
        String url = "/api/v1/finlife/savingProduct";

        // then
        MockHttpServletRequestBuilder requestBuilder = get(url)
                .params(params)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder);
    }
}
