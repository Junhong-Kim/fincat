package com.wagoowagoo.fincat.api.product.dto;

import com.wagoowagoo.fincat.api.product.entity.ProductBookmark;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class ProductResponse {

    private ProductResponse() {
        throw new IllegalStateException("Response 클래스는 인스턴스 생성이 금지되어 있습니다.");
    }

    @Getter
    public static class GetBookmarkList implements Serializable {

        private static final long serialVersionUID = 2721606453240952774L;

        private final long totalCount;
        private final List<ProductDto.Bookmark> bookmarkList;

        public GetBookmarkList(long totalCount, List<ProductBookmark> bookmarkList) {
            this.totalCount = totalCount;
            this.bookmarkList = bookmarkList.stream()
                    .map(bookmark -> ProductDto.Bookmark.builder()
                            .productCode(bookmark.getProductCode())
                            .finCompanyCode(bookmark.getFinCompanyCode())
                            .build())
                    .collect(Collectors.toList());
        }
    }
}
