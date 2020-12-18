package com.wagoowagoo.fincat.api.product.service;

import com.wagoowagoo.fincat.api.account.entity.Account;
import com.wagoowagoo.fincat.api.account.service.AccountService;
import com.wagoowagoo.fincat.api.product.dto.ProductDto;
import com.wagoowagoo.fincat.api.product.entity.ProductBookmark;
import com.wagoowagoo.fincat.api.product.repository.ProductBookmarkRepository;
import com.wagoowagoo.fincat.util.JwtUtil;
import com.wagoowagoo.fincat.util.RequestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final AccountService accountService;
    private final ProductBookmarkRepository productBookmarkRepository;

    public void createBookmark(HttpServletRequest request, ProductDto.CreateBookmark dto) {
        String accessToken = RequestUtil.getAccessToken(request);
        String username = JwtUtil.extractUsername(accessToken);
        Account account = accountService.getAccountByEmail(username);

        ProductBookmark productBookmark = ProductBookmark.builder()
                .account(account)
                .productType(dto.getProductType())
                .productCode(dto.getProductCode())
                .finCompanyCode(dto.getFinCompanyCode())
                .build();
        productBookmarkRepository.save(productBookmark);
    }
}
