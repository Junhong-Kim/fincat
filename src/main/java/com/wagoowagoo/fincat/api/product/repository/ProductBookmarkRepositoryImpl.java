package com.wagoowagoo.fincat.api.product.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wagoowagoo.fincat.api.account.entity.Account;
import com.wagoowagoo.fincat.api.product.entity.ProductBookmark;
import com.wagoowagoo.fincat.common.ErrorCode;
import com.wagoowagoo.fincat.exception.ApiException;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

import static com.wagoowagoo.fincat.api.product.entity.QProductBookmark.productBookmark;

public class ProductBookmarkRepositoryImpl implements ProductBookmarkRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ProductBookmarkRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<ProductBookmark> getBookmarkList(Account account, Pageable pageable) {
        QueryResults<ProductBookmark> results = queryFactory
                .selectFrom(productBookmark)
                .where(productBookmark.account.accountId.eq(account.getAccountId()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return results.getResults();
    }

    @Override
    public void deleteBookmark(Account account, long bookmarkId) {
        long deletedRows = queryFactory
                .delete(productBookmark)
                .where(
                        productBookmark.account.accountId.eq(account.getAccountId()),
                        productBookmark.productBookmarkId.eq(bookmarkId)
                )
                .execute();

        if (deletedRows == 0)
            throw new ApiException(ErrorCode.DELETE_BOOKMARK_ERROR);
    }
}
