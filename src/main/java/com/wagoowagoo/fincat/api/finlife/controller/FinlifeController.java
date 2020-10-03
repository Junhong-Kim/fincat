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
                        .announcedAt(obj.getDcls_month())
                        .finCompanyCode(obj.getFin_co_no())
                        .finCompanyName(obj.getKor_co_nm())
                        .pic(obj.getDcls_chrg_man())
                        .homepage(obj.getHomp_url())
                        .tel(obj.getCal_tel())
                        .build())
                .collect(Collectors.toList());
        return new SuccessResponse<>(data);
    }
}
