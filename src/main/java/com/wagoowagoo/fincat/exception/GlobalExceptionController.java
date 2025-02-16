package com.wagoowagoo.fincat.exception;

import com.wagoowagoo.fincat.common.ErrorCode;
import com.wagoowagoo.fincat.common.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler({MissingServletRequestParameterException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> parameterException(Exception e) {
        log.error(e.toString());
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.REQUEST_ERROR), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ApiException.class})
    public ResponseEntity<ErrorResponse> apiExceptionHandler(ApiException e) {
        ErrorCode errorCode = ErrorCode.valueOf(e.getMessage());
        return new ResponseEntity<>(new ErrorResponse(errorCode), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({BindException.class})
    public ResponseEntity<ErrorResponse> bindExceptionHandler(BindException e) {
        log.error(e.toString());
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception e) {
        log.error(e.toString());
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.UNEXPECTED_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
