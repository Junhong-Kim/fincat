package com.wagoowagoo.fincat.finlife.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wagoowagoo.fincat.finlife.dto.FinlifeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FinlifeService {

    private static final String RESPONSE_TYPE = ".json";
    private static final String FINANCE_COMPANY = "/companySearch"; // 금융회사
    private static final String DEPOSIT_PRODUCTS = "/depositProductsSearch"; // 정기예금
    private static final String SAVING_PRODUCTS = "/savingProductsSearch"; // 적금
    private static final String ANNUITY_SAVING_PRODUCTS = "/annuitySavingProductsSearch"; // 연금저축
    private static final String MORTGAGE_LOAN_PRODUCTS = "/mortgageLoanProductsSearch"; // 주택담보대출
    private static final String RENT_HOUSE_LOAN_PRODUCTS = "/rentHouseLoanProductsSearch"; // 전세자금대출
    private static final String CREDIT_LOAN_PRODUCTS = "/creditLoanProductsSearch"; // 개인신용대출

    private final String finlifeAuth = System.getenv("FINLIFE_AUTH");

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${finlife.url}")
    private String finlifeUrl;

    // TODO: Cache 처리
    public List<FinlifeDto.FinanceCompany> getFinanceCompanyList(String topFinGrpNo, int pageNo) {
        String apiUrl = finlifeUrl + FINANCE_COMPANY + RESPONSE_TYPE +
                "?auth=" + finlifeAuth +
                "&topFinGrpNo=" + topFinGrpNo +
                "&pageNo=" + pageNo;

        ResponseEntity<String> exchange = restTemplate.exchange(apiUrl, HttpMethod.GET, null, String.class);
        JsonObject jsonResponse = JsonParser.parseString(Objects.requireNonNull(exchange.getBody())).getAsJsonObject();
        List<FinlifeDto.FinanceCompany> financeCompanyList = new ArrayList<>();

        JsonObject result = jsonResponse.get("result").getAsJsonObject();
        JsonArray baseList = result.get("baseList").getAsJsonArray();
        baseList.forEach(jsonObject -> {
            try {
                FinlifeDto.FinanceCompany financeCompany = objectMapper.readValue(jsonObject.toString(), FinlifeDto.FinanceCompany.class);
                financeCompanyList.add(financeCompany);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        return financeCompanyList;
    }
}
