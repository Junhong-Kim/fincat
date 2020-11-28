package com.wagoowagoo.fincat.api.finlife.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wagoowagoo.fincat.api.finlife.dto.FinlifeObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FinlifeService {

    private static final String RESPONSE_TYPE = ".json";
    private static final String FINLIFE_RESULT = "result";
    private static final String FINLIFE_BASE_LIST = "baseList";
    private static final String FINLIFE_OPTION_LIST = "optionList";
    private static final String FINLIFE_TOTAL_COUNT = "total_count";
    private static final String FINLIFE_MAX_PAGE = "max_page_no";
    private static final String FINLIFE_NOW_PAGE = "now_page_no";

    private static final String FINANCE_COMPANY = "/companySearch"; // 금융회사
    private static final String DEPOSIT_PRODUCTS = "/depositProductsSearch"; // 정기예금
    private static final String SAVING_PRODUCTS = "/savingProductsSearch"; // 적금
    private static final String ANNUITY_SAVING_PRODUCTS = "/annuitySavingProductsSearch"; // 연금저축
    private static final String MORTGAGE_LOAN_PRODUCTS = "/mortgageLoanProductsSearch"; // 주택담보대출
    private static final String RENT_HOUSE_LOAN_PRODUCTS = "/rentHouseLoanProductsSearch"; // 전세자금대출
    private static final String CREDIT_LOAN_PRODUCTS = "/creditLoanProductsSearch"; // 개인신용대출

    private final String FINLIFE_AUTH = System.getenv("FINLIFE_AUTH");

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${finlife.url}")
    private String finlifeUrl;

    /***
     * 금융회사 목록 조회
     * @param topFinGrpNo 권역코드
     * @param pageNo 페이지 번호
     * @return 금융회사 목록
     */
    public FinlifeObjectMapper.FinanceCompanyList getFinanceCompanyList(String topFinGrpNo, int pageNo) {
        String apiUrl = createFinlifeApiUrl(topFinGrpNo, null, pageNo, FINANCE_COMPANY);

        // api 요청
        ResponseEntity<String> exchange = restTemplate.exchange(apiUrl, HttpMethod.GET, null, String.class);
        JsonObject jsonResponse = JsonParser.parseString(Objects.requireNonNull(exchange.getBody())).getAsJsonObject();
        JsonObject result = jsonResponse.get(FINLIFE_RESULT).getAsJsonObject();

        FinlifeObjectMapper.FinanceCompanyList financeCompanyList = new FinlifeObjectMapper.FinanceCompanyList(
                result.get(FINLIFE_TOTAL_COUNT).getAsInt(),
                result.get(FINLIFE_MAX_PAGE).getAsInt(),
                result.get(FINLIFE_NOW_PAGE).getAsInt()
        );

        // 회사 정보
        result.get(FINLIFE_BASE_LIST).getAsJsonArray().forEach(jsonObject -> {
            try {
                FinlifeObjectMapper.FinanceCompany financeCompany = objectMapper.readValue(
                        jsonObject.toString(),
                        FinlifeObjectMapper.FinanceCompany.class);

                financeCompanyList.getData().add(financeCompany);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        return financeCompanyList;
    }

    /***
     * 정기예금 목록 조회
     * @param topFinGrpNo 권역코드
     * @param financeCd 금융회사 코드
     * @param pageNo 페이지 번호
     * @return 정기예금 목록
     */
    public FinlifeObjectMapper.DepositProductList getDepositProductList(String topFinGrpNo, String financeCd, int pageNo) {
        String apiUrl = createFinlifeApiUrl(topFinGrpNo, financeCd, pageNo, DEPOSIT_PRODUCTS);

        // api 요청
        ResponseEntity<String> exchange = restTemplate.exchange(apiUrl, HttpMethod.GET, null, String.class);
        JsonObject jsonResponse = JsonParser.parseString(Objects.requireNonNull(exchange.getBody())).getAsJsonObject();
        JsonObject result = jsonResponse.get(FINLIFE_RESULT).getAsJsonObject();

        FinlifeObjectMapper.DepositProductList depositProductList = new FinlifeObjectMapper.DepositProductList(
                result.get(FINLIFE_TOTAL_COUNT).getAsInt(),
                result.get(FINLIFE_MAX_PAGE).getAsInt(),
                result.get(FINLIFE_NOW_PAGE).getAsInt()
        );

        // 상품 정보
        result.get(FINLIFE_BASE_LIST).getAsJsonArray().forEach(jsonObject -> {
            try {
                FinlifeObjectMapper.DepositProduct depositProduct = objectMapper.readValue(
                        jsonObject.toString(),
                        FinlifeObjectMapper.DepositProduct.class);

                depositProductList.getData().put(depositProduct.getFin_prdt_cd(), depositProduct);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });

        // 상품 옵션
        result.get(FINLIFE_OPTION_LIST).getAsJsonArray().forEach(jsonObject -> {
            try {
                FinlifeObjectMapper.DepositProductOption depositProductOption = objectMapper.readValue(
                        jsonObject.toString(),
                        FinlifeObjectMapper.DepositProductOption.class);

                FinlifeObjectMapper.DepositProduct depositProduct = depositProductList.getData().get(depositProductOption.getFin_prdt_cd());
                depositProduct.getOptionList().add(depositProductOption);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        return depositProductList;
    }

    /***
     * 적금 목록 조회
     * @param topFinGrpNo 권역코드
     * @param financeCd 금융회사 코드
     * @param pageNo 페이지 번호
     * @return 적금 목록
     */
    public FinlifeObjectMapper.SavingProductList getSavingProductList(String topFinGrpNo, String financeCd, int pageNo) {
        String apiUrl = createFinlifeApiUrl(topFinGrpNo, financeCd, pageNo, SAVING_PRODUCTS);

        // api 요청
        ResponseEntity<String> exchange = restTemplate.exchange(apiUrl, HttpMethod.GET, null, String.class);
        JsonObject jsonResponse = JsonParser.parseString(Objects.requireNonNull(exchange.getBody())).getAsJsonObject();
        JsonObject result = jsonResponse.get(FINLIFE_RESULT).getAsJsonObject();

        FinlifeObjectMapper.SavingProductList savingProductList = new FinlifeObjectMapper.SavingProductList(
                result.get(FINLIFE_TOTAL_COUNT).getAsInt(),
                result.get(FINLIFE_MAX_PAGE).getAsInt(),
                result.get(FINLIFE_NOW_PAGE).getAsInt()
        );

        // 상품 정보
        result.get(FINLIFE_BASE_LIST).getAsJsonArray().forEach(jsonObject -> {
            try {
                FinlifeObjectMapper.SavingProduct savingProduct = objectMapper.readValue(
                        jsonObject.toString(),
                        FinlifeObjectMapper.SavingProduct.class);

                savingProductList.getData().put(savingProduct.getFin_prdt_cd(), savingProduct);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });

        // 상품 옵션
        result.get(FINLIFE_OPTION_LIST).getAsJsonArray().forEach(jsonObject -> {
            try {
                FinlifeObjectMapper.SavingProductOption savingProductOption = objectMapper.readValue(
                        jsonObject.toString(),
                        FinlifeObjectMapper.SavingProductOption.class);

                FinlifeObjectMapper.SavingProduct savingProduct = savingProductList.getData().get(savingProductOption.getFin_prdt_cd());
                savingProduct.getOptionList().add(savingProductOption);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        return savingProductList;
    }

    /***
     * 금융상품 API URL 생성
     * @param topFinGrpNo 권역코드
     * @param financeCd 금융회사 코드
     * @param pageNo 페이지 번호
     * @param api API 주소
     */
    private String createFinlifeApiUrl(String topFinGrpNo, String financeCd, int pageNo, String api) {
        StringBuilder apiUrl = new StringBuilder()
                .append(finlifeUrl).append(api).append(RESPONSE_TYPE)
                .append("?auth=").append(FINLIFE_AUTH)
                .append("&pageNo=").append(pageNo)
                .append("&topFinGrpNo=").append(topFinGrpNo);

        if (financeCd != null) {
            apiUrl.append("&financeCd=").append(financeCd);
        }

        return apiUrl.toString();
    }
}
