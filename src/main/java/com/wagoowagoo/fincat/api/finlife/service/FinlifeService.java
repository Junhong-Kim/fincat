package com.wagoowagoo.fincat.api.finlife.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wagoowagoo.fincat.api.common.type.ProductType;
import com.wagoowagoo.fincat.api.finlife.dto.FinlifeObjectMapper;
import com.wagoowagoo.fincat.api.finlife.dto.FinlifeRequest;
import com.wagoowagoo.fincat.common.ErrorCode;
import com.wagoowagoo.fincat.exception.ApiException;
import com.wagoowagoo.fincat.feign.FinlifeFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

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

    /**
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

    /**
     * 예/적금 목록 조회
     * @return 예/적금 목록
     */
    public FinlifeObjectMapper.GeneralProductMap getGeneralProductMap(FinlifeRequest.ProductList dto, ProductType type) {
        int maxPage;
        int nowPage = 1;
        Map<String, FinlifeObjectMapper.GeneralProduct> productMap = new HashMap<>();

        do {
            String stringResponse = getGeneralProductList(dto.getTopFinGrpNo(), nowPage, type);
            JsonObject jsonResponse = JsonParser.parseString(Objects.requireNonNull(stringResponse)).getAsJsonObject();
            JsonObject result = jsonResponse.get(FINLIFE_RESULT).getAsJsonObject();
            maxPage = result.get(FINLIFE_MAX_PAGE).getAsInt();

            setGeneralProduct(result, dto, productMap);
            setGeneralProductOption(result, productMap);

            nowPage++;
        } while (nowPage <= maxPage);

        Map<String, FinlifeObjectMapper.GeneralProduct> filteredGeneralProduct = filteringGeneralProductWithOption(productMap, dto);
        return new FinlifeObjectMapper.GeneralProductMap(filteredGeneralProduct);
    }

    /**
     * 예/적금 전체 목록 조회
     */
    private String getGeneralProductList(String topFinGrpNo, int nowPage, ProductType type) {
        switch (type) {
            case DEPOSIT:
                return finlifeFeignClient.getAllDepositProductList(FINLIFE_AUTH, topFinGrpNo, nowPage);
            case SAVING:
                return finlifeFeignClient.getAllSavingProductList(FINLIFE_AUTH, topFinGrpNo, nowPage);
            default:
                throw new ApiException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }

    /**
     * 예/적금 상품 정보 설정
     */
    private void setGeneralProduct(JsonObject result,
                                   FinlifeRequest.ProductList dto,
                                   Map<String, FinlifeObjectMapper.GeneralProduct> productMap) {
        // 상품 정보
        result.get(FINLIFE_BASE_LIST).getAsJsonArray().forEach(jsonObject -> {
            try {
                FinlifeObjectMapper.GeneralProduct generalProduct = objectMapper.readValue(
                        jsonObject.toString(),
                        FinlifeObjectMapper.GeneralProduct.class);

                // 상품 정보 필터
                if (dto.getFinanceCdList() == null || dto.getFinanceCdList().contains(generalProduct.getFin_co_no()))
                    productMap.put(generalProduct.getFin_prdt_cd(), generalProduct);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 예/적금 상품 정보 <-> 상품 옵션 매칭
     */
    private void setGeneralProductOption(JsonObject result,
                                         Map<String, FinlifeObjectMapper.GeneralProduct> productMap) {
        // 상품 옵션
        result.get(FINLIFE_OPTION_LIST).getAsJsonArray().forEach(jsonObject -> {
            try {
                FinlifeObjectMapper.GeneralProductOption generalProductOption = objectMapper.readValue(
                        jsonObject.toString(),
                        FinlifeObjectMapper.GeneralProductOption.class);

                // 상품 정보 <-> 상품 옵션 매칭
                Optional.ofNullable(productMap.get(generalProductOption.getFin_prdt_cd()))
                        .ifPresent(map -> map.getOptionList().add(generalProductOption));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 옵션으로 예/적금 목록 필터링
     * @return 예/적금 목록
     */
    private Map<String, FinlifeObjectMapper.GeneralProduct> filteringGeneralProductWithOption(
            Map<String, FinlifeObjectMapper.GeneralProduct> productMap,
            FinlifeRequest.ProductList dto)
    {
        Map<String, FinlifeObjectMapper.GeneralProduct> productMapWithOption = new HashMap<>();
        productMap.forEach((key, value) -> {
            List<FinlifeObjectMapper.GeneralProductOption> optionList = value.getOptionList();

            // 상품 옵션 필터
            if (optionList.stream().anyMatch(option -> filteringGeneralProductMap(option, dto))) {
                productMapWithOption.put(key, value);
            }
        });
        return productMapWithOption;
    }

    /**
     * 예/적금 필터링 조건
     * @return 필터링 결과
     */
    private boolean filteringGeneralProductMap(FinlifeObjectMapper.GeneralProductOption option, FinlifeRequest.ProductList dto) {
        return (Objects.requireNonNull(dto.getSaveTermList()).contains(option.getSave_trm())) &&
               (Objects.requireNonNull(dto.getInterestRateTypeList()).contains(option.getIntr_rate_type())) &&
               (dto.getInterestRate() <= option.getIntr_rate()) &&
               (dto.getMaxInterestRate() <= option.getIntr_rate2());
    }
}
