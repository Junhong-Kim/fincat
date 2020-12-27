package com.wagoowagoo.fincat.api.product.repository;

import com.querydsl.core.QueryResults;
import com.wagoowagoo.fincat.api.account.entity.Account;
import com.wagoowagoo.fincat.api.product.entity.ProductBookmark;
import com.wagoowagoo.fincat.api.product.entity.ProductType;
import org.springframework.data.domain.Pageable;

public interface ProductBookmarkRepositoryCustom {

    QueryResults<ProductBookmark> getProductBookmarkList(Account account, Pageable pageable, ProductType productType);
    void deleteProductBookmark(Account account, long productBookmarkId);
    long getProductBookmarkCount(Account account, ProductType productType);
}
