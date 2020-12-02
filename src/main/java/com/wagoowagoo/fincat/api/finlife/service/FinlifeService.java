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
    public FinlifeObjectMapper.DepositProductMap getDepositProductMap(FinlifeRequest.ProductList dto) {
        int maxPage;
        int nowPage = 1;
        Map<String, FinlifeObjectMapper.DepositProduct> productMap = new HashMap<>();

        do {
            String stringResponse = finlifeFeignClient.getAllDepositProductList(FINLIFE_AUTH, dto.getTopFinGrpNo(), nowPage);
            JsonObject jsonResponse = JsonParser.parseString(Objects.requireNonNull(stringResponse)).getAsJsonObject();
            JsonObject result = jsonResponse.get(FINLIFE_RESULT).getAsJsonObject();
            maxPage = result.get(FINLIFE_MAX_PAGE).getAsInt();

            // 상품 정보
            result.get(FINLIFE_BASE_LIST).getAsJsonArray().forEach(jsonObject -> {
                try {
                    FinlifeObjectMapper.DepositProduct depositProduct = objectMapper.readValue(
                            jsonObject.toString(),
                            FinlifeObjectMapper.DepositProduct.class);

                    // 상품 정보 필터
                    if (dto.getFinanceCdList() == null || dto.getFinanceCdList().contains(depositProduct.getFin_co_no()))
                        productMap.put(depositProduct.getFin_prdt_cd(), depositProduct);
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

                    // 상품 정보 <-> 상품 옵션 매칭
                    Optional.ofNullable(productMap.get(depositProductOption.getFin_prdt_cd()))
                            .ifPresent(map -> map.getOptionList().add(depositProductOption));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });

            nowPage++;
        } while (nowPage <= maxPage);

        // 상품 옵션 필터
        Map<String, FinlifeObjectMapper.DepositProduct> depositProductMapWithOption = getDepositProductMapWithOption(productMap, dto);
        return new FinlifeObjectMapper.DepositProductMap(depositProductMapWithOption);
    }

    /***
     * 옵션으로 정기예금 목록 조회
     * @return 정기예금 목록
     */
    private Map<String, FinlifeObjectMapper.DepositProduct> getDepositProductMapWithOption(Map<String, FinlifeObjectMapper.DepositProduct> productMap,
                                                                                           FinlifeRequest.ProductList dto) {
        Map<String, FinlifeObjectMapper.DepositProduct> productMapWithOption = new HashMap<>();
        productMap.forEach((key, value) -> {
            List<FinlifeObjectMapper.DepositProductOption> optionList = value.getOptionList();
            if (optionList.stream().anyMatch(option -> filteringDepositProductMap(option, dto))) {
                productMapWithOption.put(key, value);
            }
        });
        return productMapWithOption;
    }

    /***
     * 정기예금 필터링 조건
     * @return 필터링 결과
     */
    private boolean filteringDepositProductMap(FinlifeObjectMapper.DepositProductOption option, FinlifeRequest.ProductList dto) {
        return (Objects.requireNonNull(dto.getSaveTermList()).contains(option.getSave_trm())) &&
               (Objects.requireNonNull(dto.getInterestRateTypeList()).contains(option.getIntr_rate_type())) &&
               (dto.getInterestRate() <= option.getIntr_rate()) &&
               (dto.getMaxInterestRate() <= option.getIntr_rate2());
    }

    /***
     * 적금 목록 조회
     * @return 적금 목록
     */
    public FinlifeObjectMapper.SavingProductMap getSavingProductMap(FinlifeRequest.ProductList dto) {
        int maxPage;
        int nowPage = 1;
        Map<String, FinlifeObjectMapper.SavingProduct> productMap = new HashMap<>();

        do {
            String stringResponse = finlifeFeignClient.getAllSavingProductList(FINLIFE_AUTH, dto.getTopFinGrpNo(), nowPage);
            JsonObject jsonResponse = JsonParser.parseString(Objects.requireNonNull(stringResponse)).getAsJsonObject();
            JsonObject result = jsonResponse.get(FINLIFE_RESULT).getAsJsonObject();
            maxPage = result.get(FINLIFE_MAX_PAGE).getAsInt();

            // 상품 정보
            result.get(FINLIFE_BASE_LIST).getAsJsonArray().forEach(jsonObject -> {
                try {
                    FinlifeObjectMapper.SavingProduct savingProduct = objectMapper.readValue(
                            jsonObject.toString(),
                            FinlifeObjectMapper.SavingProduct.class);

                    productMap.put(savingProduct.getFin_prdt_cd(), savingProduct);
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

                    FinlifeObjectMapper.SavingProduct savingProduct = productMap.get(savingProductOption.getFin_prdt_cd());
                    savingProduct.getOptionList().add(savingProductOption);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });

            nowPage++;
        } while (nowPage <= maxPage);

        return new FinlifeObjectMapper.SavingProductMap(productMap);
    }
}
