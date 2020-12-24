package com.wagoowagoo.fincat.api.product.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wagoowagoo.fincat.api.account.entity.Account;
import com.wagoowagoo.fincat.api.product.entity.ProductBookmark;
import com.wagoowagoo.fincat.api.product.entity.ProductType;
import com.wagoowagoo.fincat.common.ErrorCode;
import com.wagoowagoo.fincat.exception.ApiException;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import static com.wagoowagoo.fincat.api.product.entity.QProductBookmark.productBookmark;

public class ProductBookmarkRepositoryImpl implements ProductBookmarkRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ProductBookmarkRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public QueryResults<ProductBookmark> getProductBookmarkList(Account account, Pageable pageable) {
        return queryFactory
                .selectFrom(productBookmark)
                .where(productBookmark.account.accountId.eq(account.getAccountId()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
    }

    @Override
    public void deleteProductBookmark(Account account, long productBookmarkId) {
        long deletedRows = queryFactory
                .delete(productBookmark)
                .where(
                        productBookmark.account.accountId.eq(account.getAccountId()),
                        productBookmark.productBookmarkId.eq(productBookmarkId)
                )
                .execute();

        if (deletedRows == 0)
            throw new ApiException(ErrorCode.PRODUCT_BOOKMARK_DELETE_ERROR);
    }

    @Override
    public long getProductBookmarkCount(Account account, ProductType productType) {
        return queryFactory
                .selectFrom(productBookmark)
                .where(
                        productBookmark.account.accountId.eq(account.getAccountId()),
                        productBookmark.productType.eq(productType)
                )
                .fetchCount();
    }
}
