package com.wagoowagoo.fincat.api.finlife.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

public class FinlifeDto {

    private FinlifeDto() {
        throw new IllegalStateException("DTO 클래스는 인스턴스 생성이 금지되어 있습니다.");
    }

    @Getter
    @Setter
    @Builder
    public static class FinanceCompany implements Serializable {

        private static final long serialVersionUID = -3053657621515479088L;

        private String disclosureMonth; // 공시 제출월 (YYYYMM)
        private String finCompanyCode; // 금융회사 코드
        private String finCompanyName; // 금융회사 이름
        private String pic; // 담당자 (Person In Charge)
        private String homepage; // 홈페이지 주소
        private String tel; // 고객센터 전화번호
    }

    @Getter
    @Setter
    @Builder
    public static class DepositProduct implements Serializable {

        private static final long serialVersionUID = 8014439245373499280L;

        private String disclosureMonth; // 공시 제출월 (YYYYMM)
        private String finCompanyCode; // 금융회사 코드
        private String finCompanyName; // 귬융회사 이름
        private String productCode; // 금융상품 코드
        private String productName; // 금융상품 이름
        private String joinWay; // 가입방법
        private String maturityInterest; // 만기 이자율
        private String specialCondition; // 우대조건
        private String joinDeny; // 가입제한 (1: 제한없음, 2: 서민전용, 3: 일부제한)
        private String joinMember; // 가입대상
        private String etcNote; // 기타 유의사항
        private Long maxLimit; // 최고한도
        private String disclosureStartAt; // 공시 시작일 (YYYYMMDD)
        private String disclosureEndAt; // 공시 종료일 (YYYYMMDD)
        private String finCompanySubmissionAt; // 금융회사 제출일 (YYYYMMDDHH24MI)
        private List<DepositProductOption> optionList; // 금융상품 옵션
    }

    @Getter
    @Setter
    @Builder
    public static class DepositProductOption implements Serializable {

        private static final long serialVersionUID = 3974054508715695919L;

        private String interestRateType; // 저축 금리 유형
        private String interestRateTypeName; // 저축 금리 유형명
        private String saveTerm; // 저축 기간 (단위: 월)
        private double interestRate; // 저축 금리 (소수점 2자리)
        private double maxInterestRate; // 최고 우대금리 (소수점 2자리)
    }

    @Getter
    @Setter
    @Builder
    public static class SavingProduct implements Serializable {

        private static final long serialVersionUID = 8878699771590161200L;

        private String disclosureMonth; // 공시 제출월 (YYYYMM)
        private String finCompanyCode; // 금융회사 코드
        private String finCompanyName; // 귬융회사 이름
        private String productCode; // 금융상품 코드
        private String productName; // 금융상품 이름
        private String joinWay; // 가입방법
        private String maturityInterest; // 만기 이자율
        private String specialCondition; // 우대조건
        private String joinDeny; // 가입제한 (1: 제한없음, 2: 서민전용, 3: 일부제한)
        private String joinMember; // 가입대상
        private String etcNote; // 기타 유의사항
        private Long maxLimit; // 최고한도
        private String disclosureStartAt; // 공시 시작일 (YYYYMMDD)
        private String disclosureEndAt; // 공시 종료일 (YYYYMMDD)
        private String finCompanySubmissionAt; // 금융회사 제출일 (YYYYMMDDHH24MI)
        private List<SavingProductOption> optionList; // 금융상품 옵션
    }

    @Getter
    @Setter
    @Builder
    public static class SavingProductOption implements Serializable {

        private static final long serialVersionUID = 8835630075716449448L;

        private String interestRateType; // 저축 금리 유형
        private String interestRateTypeName; // 저축 금리 유형명
        private String saveTerm; // 저축 기간 (단위: 월)
        private double interestRate; // 저축 금리 (소수점 2자리)
        private double maxInterestRate; // 최고 우대금리 (소수점 2자리)
    }
}
