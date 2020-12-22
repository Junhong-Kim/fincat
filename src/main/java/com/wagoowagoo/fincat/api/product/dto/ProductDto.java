package com.wagoowagoo.fincat.api.product.dto;

import com.wagoowagoo.fincat.api.product.entity.ProductType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

public class ProductDto {

    private ProductDto() {
        throw new IllegalStateException("DTO 클래스는 인스턴스 생성이 금지되어 있습니다.");
    }

    @Getter
    @Setter
    public static class CreateBookmark {

        private ProductType productType;

        @NotBlank
        private String productCode;

        @NotBlank
        private String finCompanyCode;
    }

    @Getter
    @Setter
    public static class BookmarkList {

        private String productCode;

        private String finCompanyCode;

        @Builder
        private BookmarkList(String productCode, String finCompanyCode) {
            this.productCode = productCode;
            this.finCompanyCode = finCompanyCode;
        }
    }
}
