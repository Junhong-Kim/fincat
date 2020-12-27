package com.wagoowagoo.fincat.api.product.service;

import com.querydsl.core.QueryResults;
import com.wagoowagoo.fincat.api.account.entity.Account;
import com.wagoowagoo.fincat.api.product.dto.ProductDto;
import com.wagoowagoo.fincat.api.product.entity.ProductBookmark;
import com.wagoowagoo.fincat.api.product.entity.ProductType;
import com.wagoowagoo.fincat.api.product.repository.ProductBookmarkRepository;
import com.wagoowagoo.fincat.common.ErrorCode;
import com.wagoowagoo.fincat.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductBookmarkRepository productBookmarkRepository;

    @Transactional
    public void createProductBookmark(Account account, ProductDto.CreateProductBookmark dto) {
        ProductBookmark productBookmark = ProductBookmark.builder()
                .account(account)
                .productType(dto.getProductType())
                .productCode(dto.getProductCode())
                .finCompanyCode(dto.getFinCompanyCode())
                .build();
        productBookmarkRepository.save(productBookmark);
    }

    public QueryResults<ProductBookmark> getProductBookmarkList(Account account, Pageable pageable, ProductType productType) {
        return productBookmarkRepository.getProductBookmarkList(account, pageable, productType);
    }

    @Transactional
    public void deleteProductBookmark(Account account, long productBookmarkId) {
        productBookmarkRepository.deleteProductBookmark(account, productBookmarkId);
    }

    public void validateProductBookmark(Account account, ProductType productType) {
        long count = productBookmarkRepository.getProductBookmarkCount(account, productType);

        if (count >= 3)
            throw new ApiException(ErrorCode.PRODUCT_BOOKMARK_EXCEED_ERROR);
    }
}
