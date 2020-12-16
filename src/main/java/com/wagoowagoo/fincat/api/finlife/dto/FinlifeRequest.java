package com.wagoowagoo.fincat.api.finlife.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

public class FinlifeRequest {

    private static final String INTEREST_RATE_TYPE = "S|M";
    private static final String SAVE_TERM = "6|12|24|36";

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
        private List<String> financeCdList;

        @NonNull
        private List<@Pattern(regexp = INTEREST_RATE_TYPE) String> interestRateTypeList;

        @NonNull
        private List<@Pattern(regexp = SAVE_TERM) String> saveTermList;

        @Min(value = 0)
        private double interestRate;

        @Min(value = 0)
        private double maxInterestRate;

        @Nullable
        private String productName;
    }
}
