package com.wagoowagoo.fincat.api.product.service;

import com.wagoowagoo.fincat.api.account.entity.Account;
import com.wagoowagoo.fincat.api.product.dto.ProductDto;
import com.wagoowagoo.fincat.api.product.entity.ProductBookmark;
import com.wagoowagoo.fincat.api.product.repository.ProductBookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductBookmarkRepository productBookmarkRepository;

    @Transactional
    public void createBookmark(Account account, ProductDto.CreateBookmark dto) {
        ProductBookmark productBookmark = ProductBookmark.builder()
                .account(account)
                .productType(dto.getProductType())
                .productCode(dto.getProductCode())
                .finCompanyCode(dto.getFinCompanyCode())
                .build();
        productBookmarkRepository.save(productBookmark);
    }
}
