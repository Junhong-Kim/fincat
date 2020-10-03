package com.wagoowagoo.fincat.api.finlife.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class FinlifeDto {

    @Getter
    @Setter
    @Builder
    public static class FinanceCompany {
        private String announcedAt; // 공시 제출일 (YYYYMMDD)
        private String finCompanyCode; // 금융회사 코드
        private String finCompanyName; // 금융회사 이름
        private String pic; // 담당자 (Person In Charge)
        private String homepage; // 홈페이지 주소
        private String tel; // 고객센터 전화번호
    }
}
