package com.wagoowagoo.fincat.api.common.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductType {

    DEPOSIT("정기예금", "DEPOSIT"),
    SAVING("적금", "SAVING");

    private final String name;
    private final String value;
}
