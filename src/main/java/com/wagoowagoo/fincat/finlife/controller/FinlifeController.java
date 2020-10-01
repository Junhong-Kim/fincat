package com.wagoowagoo.fincat.finlife.controller;

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
    public List<FinlifeDto.FinanceCompany> getFinanceCompany(@RequestParam String topFinGrpNo,
                                                             @RequestParam(defaultValue = "1") int pageNo) {
        return finlifeService.getFinanceCompanyList(topFinGrpNo, pageNo);
    }
}
