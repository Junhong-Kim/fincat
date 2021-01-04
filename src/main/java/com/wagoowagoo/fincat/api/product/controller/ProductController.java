package com.wagoowagoo.fincat.api.product.controller;

import com.querydsl.core.QueryResults;
import com.wagoowagoo.fincat.api.account.entity.Account;
import com.wagoowagoo.fincat.api.account.service.AccountService;
import com.wagoowagoo.fincat.api.product.dto.ProductDto;
import com.wagoowagoo.fincat.api.product.dto.ProductResponse;
import com.wagoowagoo.fincat.api.product.entity.ProductBookmark;
import com.wagoowagoo.fincat.api.product.entity.ProductType;
import com.wagoowagoo.fincat.api.product.service.ProductService;
import com.wagoowagoo.fincat.common.BaseResponse;
import com.wagoowagoo.fincat.common.SuccessResponse;
import com.wagoowagoo.fincat.util.RequestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "상품 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final AccountService accountService;
    private final ProductService productService;

    @ApiOperation(value = "상품 북마크 생성")
    @PostMapping("/bookmark")
    public BaseResponse createProductBookmark(HttpServletRequest request, @RequestBody ProductDto.CreateProductBookmark dto) {
        String accessToken = RequestUtil.getAccessToken(request);
        Account account = accountService.getAccount(accessToken);

        productService.validateProductBookmark(account, dto.getProductType());
        productService.createProductBookmark(account, dto);
        return new SuccessResponse<>();
    }

    @ApiOperation(value = "상품 북마크 목록 조회")
    @GetMapping("/bookmark")
    public BaseResponse getProductBookmarkList(
            HttpServletRequest request
            , @ApiParam(value = "페이지", required = true) @RequestParam int page
            , @ApiParam(value = "크기", required = true) @RequestParam int size
            , @ApiParam(value = "상품 타입", required = true, allowableValues="DEPOSIT, SAVING") @RequestParam("productType") ProductType productType
    ) {
        String accessToken = RequestUtil.getAccessToken(request);
        Account account = accountService.getAccount(accessToken);

        PageRequest pageRequest = PageRequest.of(page, size);
        QueryResults<ProductBookmark> results = productService.getProductBookmarkList(account, pageRequest, productType);
        ProductResponse.GetProductBookmarkList response = new ProductResponse.GetProductBookmarkList(results.getTotal(), results.getResults());
        return new SuccessResponse<>(response);
    }

    @ApiOperation(value = "상품 북마크 삭제")
    @DeleteMapping("/bookmark/{productBookmarkId}")
    public BaseResponse deleteProductBookmark(
            HttpServletRequest request,
            @PathVariable("productBookmarkId") long productBookmarkId)
    {
        String accessToken = RequestUtil.getAccessToken(request);
        Account account = accountService.getAccount(accessToken);

        productService.deleteProductBookmark(account, productBookmarkId);
        return new SuccessResponse<>();
    }
}
