package com.wagoowagoo.fincat.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ErrorResponse extends BaseResponse implements Serializable {

    private static final long serialVersionUID = 3918939795508424915L;

    private final String code;
    private final String message;
    private List<FieldError> errors = new ArrayList<>();

    public ErrorResponse(ErrorCode errorCode) {
        this.setResult(false);
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    private ErrorResponse(final ErrorCode errorCode, final List<FieldError> errors) {
        this(errorCode);
        this.errors = errors;
    }

    public static ErrorResponse of(ErrorCode errorCode, BindingResult bindingResult) {
        return new ErrorResponse(errorCode, FieldError.of(bindingResult));
    }

    @Getter
    @RequiredArgsConstructor
    public static class FieldError implements Serializable {

        private static final long serialVersionUID = -4751584082564623868L;

        private final String field;
        private final String value;
        private final String reason;

        private static List<FieldError> of(final BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }
}
