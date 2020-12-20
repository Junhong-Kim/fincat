package com.wagoowagoo.fincat.api.product.repository;

import com.wagoowagoo.fincat.api.account.entity.Account;

public interface ProductBookmarkRepositoryCustom {

    void deleteBookmark(Account account, long bookmarkId);
}
