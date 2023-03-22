package com.mspark.blogsearch.api.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.MissingRequestValueException;
import reactor.core.publisher.Mono;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/21
 * @Time : 19:41
 */
@Slf4j
@ControllerAdvice
public class ApiApplicationExceptionHandler {

    @ExceptionHandler(MissingRequestValueException.class)
    @ResponseBody
    public Mono<ResponseEntity> handleMissingRequestValueException(MissingRequestValueException e) {
        String errMessage = e.getMessage();
        log.warn("msg = {}", errMessage);

        return Mono.just(ResponseEntity.badRequest().body(errMessage));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseBody
    public Mono<ResponseEntity> handleWebExchangeBindException(WebExchangeBindException e) {
        String errMessage = e.getMessage();
        log.warn("msg = {}", errMessage);

        return Mono.just(ResponseEntity.badRequest().body(errMessage));
    }

    @ExceptionHandler(ApiException.class)
    @ResponseBody
    public Mono<ResponseEntity> handleApiException(ApiException e) {
        String errMessage = e.getMessage();
        log.warn("msg = {}", errMessage);

        return Mono.just(ResponseEntity.internalServerError().body(errMessage));
    }

}
