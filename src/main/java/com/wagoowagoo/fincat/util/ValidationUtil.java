package com.wagoowagoo.fincat.util;

import com.wagoowagoo.fincat.common.ErrorCode;
import com.wagoowagoo.fincat.exception.ApiException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationUtil {

    public static void validatePermission(String createdBy, String username) {
        if (!createdBy.equals(username))
            throw new ApiException(ErrorCode.PERMISSION_ERROR);
    }
}

