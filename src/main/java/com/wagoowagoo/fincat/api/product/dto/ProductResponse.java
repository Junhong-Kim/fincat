package com.wagoowagoo.fincat.api.product.dto;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class ProductResponse {

    private ProductResponse() {
        throw new IllegalStateException("Response 클래스는 인스턴스 생성이 금지되어 있습니다.");
    }

    @Getter
    public static class GetProductBookmarkList implements Serializable {

        private static final long serialVersionUID = 2721606453240952774L;

        private final long totalCount;
        private final List<ProductDto.ProductBookmark> productBookmarkList;

        public GetProductBookmarkList(long totalCount, List<com.wagoowagoo.fincat.api.product.entity.ProductBookmark> productBookmarkList) {
            this.totalCount = totalCount;
            this.productBookmarkList = productBookmarkList.stream()
                    .map(productBookmark -> ProductDto.ProductBookmark.builder()
                            .productCode(productBookmark.getProductCode())
                            .finCompanyCode(productBookmark.getFinCompanyCode())
                            .build())
                    .collect(Collectors.toList());
        }
    }
}
