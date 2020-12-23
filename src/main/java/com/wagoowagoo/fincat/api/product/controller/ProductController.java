package com.wagoowagoo.fincat.api.product.controller;

import com.querydsl.core.QueryResults;
import com.wagoowagoo.fincat.api.account.entity.Account;
import com.wagoowagoo.fincat.api.account.service.AccountService;
import com.wagoowagoo.fincat.api.product.dto.ProductDto;
import com.wagoowagoo.fincat.api.product.dto.ProductResponse;
import com.wagoowagoo.fincat.api.product.entity.ProductBookmark;
import com.wagoowagoo.fincat.api.product.service.ProductService;
import com.wagoowagoo.fincat.common.BaseResponse;
import com.wagoowagoo.fincat.common.SuccessResponse;
import com.wagoowagoo.fincat.util.RequestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final AccountService accountService;
    private final ProductService productService;

    @PostMapping("/bookmark")
    public BaseResponse createBookmark(HttpServletRequest request, @RequestBody ProductDto.CreateBookmark dto) {
        String accessToken = RequestUtil.getAccessToken(request);
        Account account = accountService.getAccount(accessToken);

        productService.createBookmark(account, dto);
        return new SuccessResponse<>();
    }

    @GetMapping("/bookmark")
    public BaseResponse getBookmarkList(HttpServletRequest request, Pageable pageable) {
        String accessToken = RequestUtil.getAccessToken(request);
        Account account = accountService.getAccount(accessToken);

        QueryResults<ProductBookmark> results = productService.getBookmarkList(account, pageable);
        ProductResponse.GetBookmarkList response = new ProductResponse.GetBookmarkList(results.getTotal(), results.getResults());
        return new SuccessResponse<>(response);
    }

    @DeleteMapping("/bookmark/{bookmarkId}")
    public BaseResponse deleteBookmark(
            HttpServletRequest request,
            @PathVariable("bookmarkId") long bookmarkId)
    {
        String accessToken = RequestUtil.getAccessToken(request);
        Account account = accountService.getAccount(accessToken);

        productService.deleteBookmark(account, bookmarkId);
        return new SuccessResponse<>();
    }
}
