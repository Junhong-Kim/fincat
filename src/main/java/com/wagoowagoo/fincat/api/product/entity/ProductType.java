package com.wagoowagoo.fincat.api.product.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductType {

    DEPOSIT("정기예금", "DEPOSIT"),
    SAVING("적금", "SAVING");

    public static final String REGEX = "DEPOSIT|SAVING";

    private final String name;
    private final String value;
}
