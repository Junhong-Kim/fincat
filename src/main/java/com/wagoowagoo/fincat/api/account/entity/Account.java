package com.wagoowagoo.fincat.api.account.entity;

import com.wagoowagoo.fincat.api.common.entity.BaseEntity;
import com.wagoowagoo.fincat.api.product.entity.ProductBookmark;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class Account extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private String email;

    private String password;

    private String accessToken;

    @OneToMany(mappedBy = "account")
    private final List<ProductBookmark> productBookmarkList = new ArrayList<>();
}
