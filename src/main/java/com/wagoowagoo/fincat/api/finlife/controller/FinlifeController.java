package com.wagoowagoo.fincat.api.finlife.controller;

import com.wagoowagoo.fincat.api.finlife.dto.FinlifeObjectMapper;
import com.wagoowagoo.fincat.api.finlife.dto.FinlifeRequest;
import com.wagoowagoo.fincat.api.finlife.dto.FinlifeResponse;
import com.wagoowagoo.fincat.api.finlife.service.FinlifeService;
import com.wagoowagoo.fincat.common.BaseResponse;
import com.wagoowagoo.fincat.common.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/finlife")
public class FinlifeController {

    private final FinlifeService finlifeService;

    @Autowired
    public FinlifeController(FinlifeService finlifeService) {
        this.finlifeService = finlifeService;
    }

    @Cacheable(value = "financeCompany", key = "{#dto.topFinGrpNo, #dto.pageNo}")
    @GetMapping("/financeCompany")
    public BaseResponse getFinanceCompany(@Valid FinlifeRequest.FinanceCompany dto) {
        FinlifeObjectMapper.FinanceCompanyList financeCompanyList = finlifeService.getFinanceCompanyList(dto);

        FinlifeResponse.GetFinanceCompany response = new FinlifeResponse.GetFinanceCompany(financeCompanyList);
        return new SuccessResponse<>(response);
    }

//    @Cacheable(value = "depositProduct", key = "{#dto.topFinGrpNo, #dto.financeCd, #dto.pageNo}")
    @GetMapping("/depositProduct")
    public BaseResponse getDepositProductList(@Valid FinlifeRequest.ProductList dto) {
        FinlifeObjectMapper.DepositProductMap depositProductMap = finlifeService.getDepositProductMap(dto);

        FinlifeResponse.GetDepositProductList response = new FinlifeResponse.GetDepositProductList(depositProductMap);
        return new SuccessResponse<>(response);
    }

//    @Cacheable(value = "savingProduct", key = "{#dto.topFinGrpNo, #dto.financeCd, #dto.pageNo}")
    @GetMapping("/savingProduct")
    public BaseResponse getSavingProductList(@Valid FinlifeRequest.ProductList dto) {
        FinlifeObjectMapper.SavingProductMap savingProductMap = finlifeService.getSavingProductMap(dto);

        FinlifeResponse.GetSavingProductList  response = new FinlifeResponse.GetSavingProductList(savingProductMap);
        return new SuccessResponse<>(response);
    }
}
