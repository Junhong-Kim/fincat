package com.wagoowagoo.fincat.api.finlife.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class FinlifeObjectMapper {

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
    @Setter
    public static class DepositProduct {
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
        private List<DepositProductOption> optionList = new ArrayList<>();
    }

    @Getter
    @Setter
    public static class DepositProductOption {
        private String dcls_month;
        private String fin_co_no;
        private String fin_prdt_cd;
        private String intr_rate_type;
        private String intr_rate_type_nm;
        private String save_trm;
        private double intr_rate;
        private double intr_rate2;
    }

    @Getter
    @Setter
    public static class SavingProduct {
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
        private List<SavingProductOption> optionList = new ArrayList<>();
    }

    @Getter
    @Setter
    public static class SavingProductOption {
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
