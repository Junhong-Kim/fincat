package com.wagoowagoo.fincat.api.product.controller;

import com.wagoowagoo.fincat.api.product.dto.ProductDto;
import com.wagoowagoo.fincat.api.product.service.ProductService;
import com.wagoowagoo.fincat.common.BaseResponse;
import com.wagoowagoo.fincat.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/bookmark")
    public BaseResponse createBookmark(HttpServletRequest request, @RequestBody ProductDto.CreateBookmark dto) {
        productService.createBookmark(request, dto);
        return new SuccessResponse<>();
    }
}
