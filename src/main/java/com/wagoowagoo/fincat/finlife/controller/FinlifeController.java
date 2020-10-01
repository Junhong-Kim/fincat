package com.wagoowagoo.fincat.finlife.controller;

import com.wagoowagoo.fincat.common.BaseResponse;
import com.wagoowagoo.fincat.common.SuccessResponse;
import com.wagoowagoo.fincat.finlife.dto.FinlifeDto;
import com.wagoowagoo.fincat.finlife.service.FinlifeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        List<FinlifeDto.FinanceCompany> data = finlifeService.getFinanceCompanyList(topFinGrpNo, pageNo);
        return new SuccessResponse<>(data);
    }
}
