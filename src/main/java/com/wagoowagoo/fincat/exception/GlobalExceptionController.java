package com.wagoowagoo.fincat.exception;

import com.wagoowagoo.fincat.common.ErrorCode;
import com.wagoowagoo.fincat.common.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception e) {
        log.error(e.toString());
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.UNEXPECTED_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
