package com.wagoowagoo.fincat.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "finlife", url = "${finlife.url}")
public interface FinlifeFeignClient {

    @GetMapping("/companySearch.json")
    String getFinanceCompanyList(
            @RequestParam("auth") String auth,
            @RequestParam("topFinGrpNo") String topFinGrpNo,
            @RequestParam("pageNo") int pageNo);

    @GetMapping("/depositProductsSearch.json")
    String getDepositProductList(
            @RequestParam("auth") String auth,
            @RequestParam("topFinGrpNo") String topFinGrpNo,
            @RequestParam("financeCd") String financeCd,
            @RequestParam("pageNo") int pageNo);

    @GetMapping("/depositProductsSearch.json")
    String getAllDepositProductList(
            @RequestParam("auth") String auth,
            @RequestParam("topFinGrpNo") String topFinGrpNo,
            @RequestParam("pageNo") int pageNo);

    @GetMapping("/savingProductsSearch.json")
    String getSavingProductList(
            @RequestParam("auth") String auth,
            @RequestParam("topFinGrpNo") String topFinGrpNo,
            @RequestParam("financeCd") String financeCd,
            @RequestParam("pageNo") int pageNo);

    @GetMapping("/savingProductsSearch.json")
    String getAllSavingProductList(
            @RequestParam("auth") String auth,
            @RequestParam("topFinGrpNo") String topFinGrpNo,
            @RequestParam("pageNo") int pageNo);
}
