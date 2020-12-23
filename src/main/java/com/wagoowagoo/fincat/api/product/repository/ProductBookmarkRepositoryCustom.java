package com.wagoowagoo.fincat.api.product.repository;

import com.querydsl.core.QueryResults;
import com.wagoowagoo.fincat.api.account.entity.Account;
import com.wagoowagoo.fincat.api.product.entity.ProductBookmark;
import org.springframework.data.domain.Pageable;

public interface ProductBookmarkRepositoryCustom {

    QueryResults<ProductBookmark> getBookmarkList(Account account, Pageable pageable);
    void deleteBookmark(Account account, long bookmarkId);
}
