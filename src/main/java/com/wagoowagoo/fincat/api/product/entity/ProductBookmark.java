package com.wagoowagoo.fincat.api.product.entity;

import com.wagoowagoo.fincat.api.account.entity.Account;
import com.wagoowagoo.fincat.api.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_bookmark")
public class ProductBookmark extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productBookmarkId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Enumerated(EnumType.STRING)
    private ProductType productType;

    private String productCode;

    private String finCompanyCode;
}
