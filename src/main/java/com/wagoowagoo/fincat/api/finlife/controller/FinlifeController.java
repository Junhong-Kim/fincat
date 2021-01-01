package com.wagoowagoo.fincat.api.finlife.controller;

import com.wagoowagoo.fincat.api.finlife.dto.FinlifeObjectMapper;
import com.wagoowagoo.fincat.api.finlife.dto.FinlifeRequest;
import com.wagoowagoo.fincat.api.finlife.dto.FinlifeResponse;
import com.wagoowagoo.fincat.api.finlife.service.FinlifeService;
import com.wagoowagoo.fincat.api.product.entity.ProductType;
import com.wagoowagoo.fincat.common.BaseResponse;
import com.wagoowagoo.fincat.common.SuccessResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "금융상품 오픈 API")
@RestController
@RequestMapping("/api/v1/finlife")
public class FinlifeController {

    private final FinlifeService finlifeService;

    @Autowired
    public FinlifeController(FinlifeService finlifeService) {
        this.finlifeService = finlifeService;
    }

//    @Cacheable(value = "financeCompany", key = "{#dto.topFinGrpNo, #dto.pageNo}")
    @ApiOperation(value = "금융회사 목록 조회")
    @GetMapping("/financeCompany")
    public BaseResponse getFinanceCompany(@Valid FinlifeRequest.FinanceCompany dto) {
        FinlifeObjectMapper.FinanceCompanyList financeCompanyList = finlifeService.getFinanceCompanyList(dto);

        FinlifeResponse.GetFinanceCompany response = new FinlifeResponse.GetFinanceCompany(financeCompanyList);
        return new SuccessResponse<>(response);
    }

//    @Cacheable(value = "depositProduct", key = "{#dto.topFinGrpNo, #dto.financeCd, #dto.pageNo}")
    @ApiOperation(value = "예금상품 목록 조회")
    @GetMapping("/depositProduct")
    public BaseResponse getDepositProductList(@Valid FinlifeRequest.ProductList dto) {
        FinlifeObjectMapper.GeneralProductMap generalProductMap = finlifeService.getGeneralProductMap(dto, ProductType.DEPOSIT);

        FinlifeResponse.GetGeneralProductList response = new FinlifeResponse.GetGeneralProductList(generalProductMap);
        return new SuccessResponse<>(response);
    }

//    @Cacheable(value = "savingProduct", key = "{#dto.topFinGrpNo, #dto.financeCd, #dto.pageNo}")품
    @ApiOperation(value = "적금상품 목록 조회")
    @GetMapping("/savingProduct")
    public BaseResponse getSavingProductList(@Valid FinlifeRequest.ProductList dto) {
        FinlifeObjectMapper.GeneralProductMap generalProductMap = finlifeService.getGeneralProductMap(dto, ProductType.SAVING);

        FinlifeResponse.GetGeneralProductList response = new FinlifeResponse.GetGeneralProductList(generalProductMap);
        return new SuccessResponse<>(response);
    }
}
