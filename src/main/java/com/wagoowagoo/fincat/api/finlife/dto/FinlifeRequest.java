package com.wagoowagoo.fincat.api.finlife.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;

public class FinlifeRequest {

    private FinlifeRequest() {
        throw new IllegalStateException("Request 클래스는 인스턴스 생성이 금지되어 있습니다.");
    }

    @Getter
    @Setter
    public static class FinanceCompany {

        @NotBlank
        private String topFinGrpNo;

        private int pageNo = 1;
    }

    @Getter
    @Setter
    public static class ProductList {

        @NotBlank
        private String topFinGrpNo;

        @Nullable
        private String financeCd;

        private int pageNo = 1;

        @Nullable
        private String interestRateType;

        @Nullable
        private String saveTerm;

        private double interestRate;
        private double maxInterestRate;
    }
}
