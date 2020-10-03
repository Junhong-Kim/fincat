package com.wagoowagoo.fincat.api.finlife.controller;

import com.wagoowagoo.fincat.api.finlife.dto.FinlifeDto;
import com.wagoowagoo.fincat.api.finlife.dto.FinlifeObjectMapper;
import com.wagoowagoo.fincat.api.finlife.service.FinlifeService;
import com.wagoowagoo.fincat.common.BaseResponse;
import com.wagoowagoo.fincat.common.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/finlife")
public class FinlifeController {

    private final FinlifeService finlifeService;

    @Autowired
    public FinlifeController(FinlifeService finlifeService) {
        this.finlifeService = finlifeService;
    }

    @GetMapping("/financeCompany")
    public BaseResponse getFinanceCompany(@RequestParam String topFinGrpNo,
                                          @RequestParam(defaultValue = "1") int pageNo) {
        List<FinlifeObjectMapper.FinanceCompany> list = finlifeService.getFinanceCompanyList(topFinGrpNo, pageNo);
        List<FinlifeDto.FinanceCompany> data = list.stream().map(obj ->
                FinlifeDto.FinanceCompany.builder()
                        .disclosureMonth(obj.getDcls_month())
                        .finCompanyCode(obj.getFin_co_no())
                        .finCompanyName(obj.getKor_co_nm())
                        .pic(obj.getDcls_chrg_man())
                        .homepage(obj.getHomp_url())
                        .tel(obj.getCal_tel())
                        .build())
                .collect(Collectors.toList());
        return new SuccessResponse<>(data);
    }

    @GetMapping("/depositProduct")
    public BaseResponse getDepositProductList(@RequestParam String topFinGrpNo,
                                              @RequestParam String financeCd,
                                              @RequestParam(defaultValue = "1") int pageNo) {
        HashMap<String, FinlifeObjectMapper.DepositProduct> hashMap =
                finlifeService.getDepositProductList(topFinGrpNo, financeCd, pageNo);
        List<FinlifeDto.DepositProduct> data = hashMap.values().stream().map(value ->
                FinlifeDto.DepositProduct.builder()
                        .disclosureMonth(value.getDcls_month())
                        .finCompanyCode(value.getFin_co_no())
                        .finCompanyName(value.getKor_co_nm())
                        .productCode(value.getFin_prdt_cd())
                        .productName(value.getFin_prdt_nm())
                        .joinWay(value.getJoin_way())
                        .joinDeny(value.getJoin_deny())
                        .joinMember(value.getJoin_member())
                        .maturityInterest(value.getMtrt_int())
                        .specialCondition(value.getSpcl_cnd())
                        .etcNote(value.getEtc_note())
                        .maxLimit(value.getMax_limit())
                        .disclosureStartAt(value.getDcls_strt_day())
                        .disclosureEndAt(value.getDcls_end_day())
                        .finCompanySubmissionAt(value.getFin_co_subm_day())
                        .optionList(value.getOptionList().stream().map(obj ->
                                FinlifeDto.DepositProductOption.builder()
                                        .interestRateType(obj.getIntr_rate_type())
                                        .interestRateTypeName(obj.getIntr_rate_type_nm())
                                        .interestRate(obj.getIntr_rate())
                                        .maxInterestRate(obj.getIntr_rate2())
                                        .saveTerm(obj.getSave_trm())
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
        return new SuccessResponse<>(data);
    }
}
