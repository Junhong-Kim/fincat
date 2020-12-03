package com.wagoowagoo.fincat.api.finlife.dto;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class FinlifeResponse {

    private FinlifeResponse() {
        throw new IllegalStateException("Response 클래스는 인스턴스 생성이 금지되어 있습니다.");
    }

    @Getter
    public static class GetFinanceCompany implements Serializable {

        private static final long serialVersionUID = 514348367207038867L;

        private final int totalCount;
        private final int maxPage;
        private final int nowPage;
        private final List<FinlifeDto.FinanceCompany> financeCompanyList;

        public GetFinanceCompany(FinlifeObjectMapper.FinanceCompanyList financeCompanyList) {
            this.totalCount = financeCompanyList.getTotalCount();
            this.maxPage = financeCompanyList.getMaxPage();
            this.nowPage = financeCompanyList.getNowPage();
            this.financeCompanyList = financeCompanyList.getData().stream().map(obj ->
                    FinlifeDto.FinanceCompany.builder()
                            .disclosureMonth(obj.getDcls_month())
                            .finCompanyCode(obj.getFin_co_no())
                            .finCompanyName(obj.getKor_co_nm())
                            .pic(obj.getDcls_chrg_man())
                            .homepage(obj.getHomp_url())
                            .tel(obj.getCal_tel())
                            .build())
                    .collect(Collectors.toList());
        }
    }

    @Getter
    public static class GetGeneralProductList implements Serializable {

        private static final long serialVersionUID = -7529838533825460353L;

        private final int totalCount;
        private final List<FinlifeDto.GeneralProduct> productList;

        public GetGeneralProductList(FinlifeObjectMapper.GeneralProductMap generalProductMap) {
                this.totalCount = generalProductMap.getData().size();
                this.productList = generalProductMap.getData().values().stream().map(value ->
                            FinlifeDto.GeneralProduct.builder()
                                    .disclosureMonth(value.getDcls_month())
                                    .finCompanyCode(value.getFin_co_no())
                                    .finCompanyName(value.getKor_co_nm())
                                    .productCode(value.getFin_prdt_cd())
                                    .productName(value.getFin_prdt_nm())
                                    .joinWay(value.getJoin_way())
                                    .joinDeny(value.getJoin_deny())
                                    .joinMember(value.getJoin_member())
                                    .maturityInterest(value.getMtrt_int())
                                    .specialCondition(value.getSpcl_cnd())
                                    .etcNote(value.getEtc_note())
                                    .maxLimit(value.getMax_limit())
                                    .disclosureStartAt(value.getDcls_strt_day())
                                    .disclosureEndAt(value.getDcls_end_day())
                                    .finCompanySubmissionAt(value.getFin_co_subm_day())
                                    .optionList(value.getOptionList().stream().map(obj ->
                                            FinlifeDto.GeneralProductOption.builder()
                                                    .interestRateType(obj.getIntr_rate_type())
                                                    .interestRateTypeName(obj.getIntr_rate_type_nm())
                                                    .interestRate(obj.getIntr_rate())
                                                    .maxInterestRate(obj.getIntr_rate2())
                                                    .saveTerm(obj.getSave_trm())
                                                    .build())
                                            .collect(Collectors.toList()))
                                    .build())
                            .collect(Collectors.toList());
        }
    }
}
