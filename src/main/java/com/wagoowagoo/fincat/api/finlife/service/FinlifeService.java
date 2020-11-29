package com.wagoowagoo.fincat.api.finlife.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wagoowagoo.fincat.api.finlife.dto.FinlifeObjectMapper;
import com.wagoowagoo.fincat.api.finlife.dto.FinlifeRequest;
import com.wagoowagoo.fincat.feign.FinlifeFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FinlifeService {

    private static final String FINLIFE_RESULT = "result";
    private static final String FINLIFE_BASE_LIST = "baseList";
    private static final String FINLIFE_OPTION_LIST = "optionList";
    private static final String FINLIFE_TOTAL_COUNT = "total_count";
    private static final String FINLIFE_MAX_PAGE = "max_page_no";
    private static final String FINLIFE_NOW_PAGE = "now_page_no";
    private static final String FINLIFE_AUTH = System.getenv("FINLIFE_AUTH");

    private final FinlifeFeignClient finlifeFeignClient;
    private final ObjectMapper objectMapper;

    /***
     * 금융회사 목록 조회
     * @return 금융회사 목록
     */
    public FinlifeObjectMapper.FinanceCompanyList getFinanceCompanyList(FinlifeRequest.FinanceCompany dto) {
        String stringResponse = finlifeFeignClient.getFinanceCompanyList(FINLIFE_AUTH, dto.getTopFinGrpNo(), dto.getPageNo());
        JsonObject jsonResponse = JsonParser.parseString(Objects.requireNonNull(stringResponse)).getAsJsonObject();
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
     * @return 정기예금 목록
     */
    public FinlifeObjectMapper.DepositProductList getDepositProductList(FinlifeRequest.ProductList dto) {
        String stringResponse = finlifeFeignClient.getDepositProductList(FINLIFE_AUTH, dto.getTopFinGrpNo(), dto.getFinanceCd(), dto.getPageNo());
        JsonObject jsonResponse = JsonParser.parseString(Objects.requireNonNull(stringResponse)).getAsJsonObject();
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
     * @return 적금 목록
     */
    public FinlifeObjectMapper.SavingProductList getSavingProductList(FinlifeRequest.ProductList dto) {
        String stringResponse = finlifeFeignClient.getSavingProductList(FINLIFE_AUTH, dto.getTopFinGrpNo(), dto.getFinanceCd(), dto.getPageNo());
        JsonObject jsonResponse = JsonParser.parseString(Objects.requireNonNull(stringResponse)).getAsJsonObject();
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
}
