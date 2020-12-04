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
            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(value = "financeCd", required = false) String financeCd);

    @GetMapping("/savingProductsSearch.json")
    String getSavingProductList(
            @RequestParam("auth") String auth,
            @RequestParam("topFinGrpNo") String topFinGrpNo,
            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(value = "financeCd", required = false) String financeCd);
}
