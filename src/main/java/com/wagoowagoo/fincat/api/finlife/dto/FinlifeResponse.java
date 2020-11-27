package com.wagoowagoo.fincat.api.finlife.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

public class FinlifeResponse {

    private FinlifeResponse() {
        throw new IllegalStateException("Response 클래스는 인스턴스 생성이 금지되어 있습니다.");
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DepositProductList implements Serializable {

        private static final long serialVersionUID = -7529838533825460353L;

        private int totalCount;
        private int maxPage;
        private int nowPage;
        private List<FinlifeDto.DepositProduct> data;
    }
}
