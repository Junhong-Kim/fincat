package com.wagoowagoo.fincat.api.product.repository;

import com.wagoowagoo.fincat.api.account.entity.Account;
import com.wagoowagoo.fincat.api.product.entity.ProductBookmark;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductBookmarkRepositoryCustom {

    List<ProductBookmark> getBookmarkList(Account account, Pageable pageable);
    void deleteBookmark(Account account, long bookmarkId);
}
