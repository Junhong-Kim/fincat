package com.wagoowagoo.fincat.api.finlife.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FinlifeObjectMapper {

    private FinlifeObjectMapper() {
        throw new IllegalStateException("ObjectMapper 클래스는 인스턴스 생성이 금지되어 있습니다.");
    }

    @Getter
    @RequiredArgsConstructor
    public static class FinanceCompanyList {

        private final int totalCount;
        private final int maxPage;
        private final int nowPage;
        private final List<FinanceCompany> data = new ArrayList<>();
    }

    @Getter
    @Setter
    public static class FinanceCompany {

        private String dcls_month;
        private String fin_co_no;
        private String kor_co_nm;
        private String dcls_chrg_man;
        private String homp_url;
        private String cal_tel;
    }

    @Getter
    @RequiredArgsConstructor
    public static class GeneralProductMap {

        private final Map<String, GeneralProduct> data;
    }

    @Getter
    @Setter
    public static class GeneralProduct {

        private String dcls_month;
        private String fin_co_no;
        private String fin_prdt_cd;
        private String kor_co_nm;
        private String fin_prdt_nm;
        private String join_way;
        private String mtrt_int;
        private String spcl_cnd;
        private String join_deny;
        private String join_member;
        private String etc_note;
        private Long max_limit;
        private String dcls_strt_day;
        private String dcls_end_day;
        private String fin_co_subm_day;
        private List<GeneralProductOption> optionList = new ArrayList<>();
    }

    @Getter
    @Setter
    public static class GeneralProductOption {

        private String dcls_month;
        private String fin_co_no;
        private String fin_prdt_cd;
        private String intr_rate_type;
        private String intr_rate_type_nm;
        private String save_trm;
        private double intr_rate;
        private double intr_rate2;
    }
}
