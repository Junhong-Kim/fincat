package com.wagoowagoo.fincat.api.finlife.dto;

import lombok.Getter;
import lombok.Setter;

public class FinlifeDto {

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
}
