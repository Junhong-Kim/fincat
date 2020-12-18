package com.wagoowagoo.fincat.api.product.dto;

import com.wagoowagoo.fincat.api.common.type.ProductType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class ProductDto {

    private ProductDto() {
        throw new IllegalStateException("DTO 클래스는 인스턴스 생성이 금지되어 있습니다.");
    }

    @Getter
    @Setter
    public static class CreateBookmark {

        @Pattern(regexp = ProductType.REGEX)
        private String productType;

        @NotBlank
        private String productCode;

        @NotBlank
        private String finCompanyCode;
    }
}
