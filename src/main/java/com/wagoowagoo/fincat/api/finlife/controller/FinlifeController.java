package com.wagoowagoo.fincat.api.finlife.controller;

import com.wagoowagoo.fincat.api.finlife.dto.FinlifeObjectMapper;
import com.wagoowagoo.fincat.api.finlife.dto.FinlifeResponse;
import com.wagoowagoo.fincat.api.finlife.service.FinlifeService;
import com.wagoowagoo.fincat.common.BaseResponse;
import com.wagoowagoo.fincat.common.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/finlife")
public class FinlifeController {

    private final FinlifeService finlifeService;

    @Autowired
    public FinlifeController(FinlifeService finlifeService) {
        this.finlifeService = finlifeService;
    }

    @Cacheable(value = "financeCompany", key = "{#topFinGrpNo, #pageNo}")
    @GetMapping("/financeCompany")
    public BaseResponse getFinanceCompany(@RequestParam String topFinGrpNo,
                                          @RequestParam(defaultValue = "1") int pageNo) {
        FinlifeObjectMapper.FinanceCompanyList financeCompanyList = finlifeService.getFinanceCompanyList(topFinGrpNo, pageNo);

        FinlifeResponse.GetFinanceCompany response = new FinlifeResponse.GetFinanceCompany(financeCompanyList);
        return new SuccessResponse<>(response);
    }

    @Cacheable(value = "depositProduct", key = "{#topFinGrpNo, #financeCd, #pageNo}")
    @GetMapping("/depositProduct")
    public BaseResponse getDepositProductList(@RequestParam String topFinGrpNo,
                                              @RequestParam(required = false) String financeCd,
                                              @RequestParam(defaultValue = "1") int pageNo) {
        FinlifeObjectMapper.DepositProductList depositProductList = finlifeService.getDepositProductList(topFinGrpNo, financeCd, pageNo);

        FinlifeResponse.GetDepositProductList response = new FinlifeResponse.GetDepositProductList(depositProductList);
        return new SuccessResponse<>(response);
    }

    @Cacheable(value = "savingProduct", key = "{#topFinGrpNo, #financeCd, #pageNo}")
    @GetMapping("/savingProduct")
    public BaseResponse getSavingProductList(@RequestParam String topFinGrpNo,
                                             @RequestParam(required = false) String financeCd,
                                             @RequestParam(defaultValue = "1") int pageNo) {
        FinlifeObjectMapper.SavingProductList savingProductList =  finlifeService.getSavingProductList(topFinGrpNo, financeCd, pageNo);

        FinlifeResponse.GetSavingProductList  response = new FinlifeResponse.GetSavingProductList(savingProductList);
        return new SuccessResponse<>(response);
    }
}
